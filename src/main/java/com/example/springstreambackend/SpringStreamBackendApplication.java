package com.example.springstreambackend;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringStreamBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStreamBackendApplication.class, args);
    }
//hello from


}
