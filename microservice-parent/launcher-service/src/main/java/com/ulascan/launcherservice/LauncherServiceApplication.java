package com.ulascan.launcherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LauncherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LauncherServiceApplication.class, args);
    }

}

