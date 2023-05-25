package com.ulascan.apigateway.filter;

import com.ulascan.apigateway.entity.Permission;
import com.ulascan.apigateway.entity.Role;
import com.ulascan.apigateway.util.AuthorizationConfig;
import com.ulascan.apigateway.util.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component

public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private AuthorizationConfig authorizationConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                ServerHttpRequest request = exchange.getRequest();
                HttpMethod method = request.getMethod();
                String path = request.getURI().getPath();


                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // template.getForObject("http://IDENTITY-SERVICE/validate?token" + authHeader, String.class);
                    //template.getForObject("http://IDENTITY-SERVICE/auth/validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);

                    // application.yml'den yetkilendirme konfigürasyonunu al
                    List<Role> requiredRole= getRequiredRoles(path, method);

                    if (requiredRole.isEmpty()) {
                        return chain.filter(exchange);
                    }


                    String userRole = jwtUtil.extractRoleFromJwt(authHeader);
                    for(Role permission : requiredRole){
                        if(userRole.equals(permission.name())){
                            System.out.println("EUREKAAAAAAAAAAAAAAA");
                            return chain.filter(exchange);
                        }
                    }
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    private List<Role> getRequiredRoles(String path, HttpMethod method) {
        for (Map<String, Object> endpoint : authorizationConfig.getEndpoints()) {
            String endpointPath = (String) endpoint.get("path");
            String endpointMethod = (String) endpoint.get("method");
            if (endpointPath.equals(path) && endpointMethod.equals(method.toString())) {
                Map<String, Object> authorities2 = (Map<String, Object>) endpoint.get("authorities");
                List<String> authorities = new ArrayList<>();
                for (Map.Entry<String, Object> entry : authorities2.entrySet()) {
                    String authority = entry.getValue().toString();
                    authorities.add(authority);
                }
                return authorities.stream()
                        .map(Role::fromString)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
    /*
    private List<Permission> getRequiredPermissions(String path, HttpMethod method) {
        Map<String, Object> authorizationConfig = environment.getProperty("authorization.endpoints", Map.class);
        if (authorizationConfig != null) {
            String key = method.toString() + " " + path;
            List<Map<String, Object>> endpoints = (List<Map<String, Object>>) authorizationConfig.get(key);
            if (endpoints != null) {
                return endpoints.stream()
                        .map(endpoint -> Permission.fromString((String) endpoint.get("authorities")))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }*/

    private String extractJwtToken(ServerHttpRequest request) {
        List<String> authorizationHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String authorizationHeader = authorizationHeaders.get(0);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7);
            }
        }
        return null;
    }

    private boolean validateJwtToken(String jwtToken) {
        String identityServiceUrl = "http://identity-service/validate"; // Identity Service URL
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                identityServiceUrl,
                HttpMethod.GET,
                requestEntity,
                Void.class);

        return responseEntity.getStatusCode() == HttpStatus.OK;
    }

    @Data
    public static class Config {
        // Configuration properties (if any)
    }
}
