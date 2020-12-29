package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutes {

    @Bean
    public RouteLocator loadBalancedRoutesConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/*", "/api/v1/beerUpc/*")
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers*", "/api/v1/customers/**")
                        .uri("lb://beer-order-service")
                        .id("beer-order-service"))
                .route(r -> r.path("/api/v1/beer/**")
                        .filters(f -> f.circuitBreaker(cb -> cb.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")))
                        .uri("lb://beer-inventory-service")
                        .id("beer-inventory-service"))
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://beer-inventory-failover-service")
                        .id("beer-inventory-failover-service"))
                .build();
    }
}
