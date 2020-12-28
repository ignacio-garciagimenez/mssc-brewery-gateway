package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfiguration {

    @Bean
    public RouteLocator googleConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/search")
                    .uri("https://google.com")
                    .id("google"))
                .build();
    }
}
