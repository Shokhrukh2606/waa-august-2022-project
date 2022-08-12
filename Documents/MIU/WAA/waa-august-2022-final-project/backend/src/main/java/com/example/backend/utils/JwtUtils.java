package com.example.backend.utils;

import com.example.backend.domain.user.LocalUser;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUtils {


    public static LocalUser getUserFromToken(Authentication authentication){
        if (authentication == null) {
            throw new UsernameNotFoundException("Should be authenticated");
        }

        var principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>)authentication.getPrincipal();
        var token = ((RefreshableKeycloakSecurityContext) authentication.getCredentials()).getToken();

        var email = token.getEmail();
        var firstName = token.getGivenName();
        var lastName = token.getFamilyName();
        var keyClockUserId = principal.getName();

        return new LocalUser(email, firstName, lastName, keyClockUserId, false, null);
    }
}
