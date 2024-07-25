package com.mk.gateway.routes;

import com.mk.gateway.configs.RouteConfigProperties;
import com.mk.gateway.domains.AppRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRouteConfig {
    @Autowired
    private RouteConfigProperties routeConfigProperties;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();

        for (AppRoute appRoute : routeConfigProperties.getPublicAppRoutes()) {
            routesBuilder.route(appRoute.getName(), r -> r.path(appRoute.getPath())
                    .uri(appRoute.getUri()));
        }
        for (AppRoute appRoute : routeConfigProperties.getAuthenticatedAppRoutes()) {
            routesBuilder.route(appRoute.getName(), r -> r.path(appRoute.getPath())
                    .uri(appRoute.getUri()));
        }

        return routesBuilder.build();
    }
}
