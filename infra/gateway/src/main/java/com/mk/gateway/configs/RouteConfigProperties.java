package com.mk.gateway.configs;

import com.mk.gateway.domains.AppRoute;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Setter
@Configuration
@ConfigurationProperties(prefix = "application.routes")
public class RouteConfigProperties {
    private List<AppRoute> publicAppRoutes;
    private List<AppRoute> authenticatedAppRoutes;

    public List<AppRoute> getPublicAppRoutes() {
        if (publicAppRoutes == null) {
            publicAppRoutes = new ArrayList<>();
        }
        return publicAppRoutes;
    }

    public List<AppRoute> getAuthenticatedAppRoutes() {
        if (authenticatedAppRoutes == null) {
            authenticatedAppRoutes = new ArrayList<>();
        }
        return authenticatedAppRoutes;
    }

}
