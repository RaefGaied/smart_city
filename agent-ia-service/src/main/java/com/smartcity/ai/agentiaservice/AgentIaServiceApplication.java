package com.smartcity.ai.agentiaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgentIaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentIaServiceApplication.class, args);
    }

}
