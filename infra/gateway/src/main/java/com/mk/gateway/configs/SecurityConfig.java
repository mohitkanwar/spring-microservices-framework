package com.mk.gateway.configs;

import com.mk.gateway.domains.AppRoute;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@Log
public class SecurityConfig {

    @Autowired
    private RouteConfigProperties routeConfigProperties;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> {
                    for (AppRoute appRoute : routeConfigProperties.getPublicAppRoutes()) {
                        exchanges.pathMatchers(appRoute.getPath()).permitAll();
                    }
                    for (AppRoute appRoute : routeConfigProperties.getAuthenticatedAppRoutes()) {
                        exchanges.pathMatchers(appRoute.getPath()).authenticated();
                    }
                    exchanges.anyExchange().denyAll();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        return http.build();
    }
}
