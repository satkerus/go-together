package org.go.together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"org.go.together.client"})
public class EventServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class);
    }
}
