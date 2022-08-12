package com.example.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "admin-keycloak.prop")
public class KeyCloakAdminProperties {

    private String realm;
    private String clientRealm;
    private String username;
    private String password;
    private String clientId;
    private String serverUrl;
}
