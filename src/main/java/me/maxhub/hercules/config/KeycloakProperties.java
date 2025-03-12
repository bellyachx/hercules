package me.maxhub.hercules.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("keycloak")
@Data
public class KeycloakProperties {
    private String url;
    private String realm;
    private String clientId;
    private String clientSecret;
}
