package com.ulascan.apigateway.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationConfig {

    private List<Map<String, Object>> endpoints = new ArrayList<>();

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        String endpointsValue = environment.getProperty("authorization.endpoints");
        if (StringUtils.hasText(endpointsValue)) {
            Yaml yaml = new Yaml();
            endpoints = yaml.load(endpointsValue);
        }
    }

    public List<Map<String, Object>> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Map<String, Object>> endpoints) {
        this.endpoints = endpoints;
    }
}