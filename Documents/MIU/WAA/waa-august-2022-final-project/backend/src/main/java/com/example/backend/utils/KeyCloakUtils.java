package com.example.backend.utils;

import com.example.backend.domain.user.Role;
import com.example.backend.property.KeyCloakAdminProperties;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class KeyCloakUtils {

    private final KeyCloakAdminProperties properties;
    private final Keycloak keycloak;

    public UsersResource getUsersResource() {
        return keycloak.realm(properties.getClientRealm()).users();
    }

    public UserResource getUserResource(String userId){
        return getUsersResource().get(userId);
    }

    public UserRepresentation getUserRepresentation(UserResource resource){
        return resource.toRepresentation();
    }

    public RoleResource getRole(Role role){
        return keycloak.realm(properties.getClientRealm()).roles().get(role.name());
    }

    public List<RoleRepresentation> roleToRealmRoleRepresentation(Role role){
        return rolesToRealmRoleRepresentation(List.of(role.name()));
    }

    private List<RoleRepresentation> rolesToRealmRoleRepresentation(List<String> roles) {
        List<RoleRepresentation> existingRoles = keycloak.realm(properties.getClientRealm())
                .roles()
                .list();

        List<String> serverRoles = existingRoles
                .stream()
                .map(RoleRepresentation::getName).toList();
        List<RoleRepresentation> resultRoles = new ArrayList<>();

        for (String role : roles) {
            int index = serverRoles.indexOf(role);
            if (index != -1) {
                resultRoles.add(existingRoles.get(index));
            }
        }
        return resultRoles;
    }
}
