package com.ulascan.serverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerServiceApplication.class, args);
    }
}
