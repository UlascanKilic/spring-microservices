package com.ulascan.apigateway.filter;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulascan.apigateway.util.AuthorizationConfig;
import com.ulascan.apigateway.util.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@Component
public class MailFilter extends AbstractGatewayFilterFactory<MailFilter.Config> {
    @Autowired
    private JwtUtil jwtUtil;

    @Data
    public static class Config {
        // Configuration properties (if any)
    }

    public MailFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config)
    {
        return ((exchange, chain) -> {

            try{
                ServerHttpRequest request = exchange.getRequest();
                String emailInBody = exchange.getAttribute("hostEmail");

                System.out.println("===========EMAIL IN BODY : " + emailInBody + "===========");
                //header contains token or not
                String emailInJWT = jwtUtil.extractEmailFromJwt(Objects
                        .requireNonNull(exchange
                                .getRequest()
                                .getHeaders()
                                .get(HttpHeaders.AUTHORIZATION))
                        .get(0));

                System.out.println("===========EMAIL IN JWT : " + emailInJWT + "===========");

                if(emailInBody != null && emailInBody.equals(emailInJWT))
                {
                    return chain.filter(exchange);
                }

                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            }
            catch (Exception e)
            {
                System.out.println("HATAAAA : " + e.getMessage());
            }
            return chain.filter(exchange);
        });
    }

    /**
     * Extracts the email field from a JSON body.
     *
     * @param jsonBody The JSON body as a String.
     * @return The extracted email as a String, or null if parsing fails.
     */
    private String extractEmailFromJson(String jsonBody){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonBody);
            return jsonNode.get("email").asText();
        } catch (IOException e) {
            // Handle JSON parsing error
        }
        return null;
    }
}
