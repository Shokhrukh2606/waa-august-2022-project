package com.example.backend.config.security;

import com.example.backend.property.FileStorageProperties;
import com.example.backend.property.KeyCloakAdminProperties;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@KeycloakConfiguration
@Import(KeycloakSpringBootConfigResolver.class)
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled= true
)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${app.cors.allowed-origins}")
    List<String> allowedOrigin;

    private final KeyCloakAdminProperties properties;

    private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"};
    private static final String[] AUTH_WHITELIST = {"/swagger-resources/**", "/v2/api-docs/**", "/swagger.json",
            "/swagger-ui.html", "/webjars/**"};

    private static final String[] ALLOWED_HEADERS = {
            "accept", "cache-Control", "authorization", "content-type", "x-auth-token", "cookie", "set-cookie",
            "content-disposition", "dnt", "if-modified-since", "keep-alive", "origin", "user-agent", "x-requested-with"
    };
    private static final long MAX_AGE = 86400L;

    public SecurityConfig(KeyCloakAdminProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/resource/uploads/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public SessionRegistry buildRegistry() {
        return new SessionRegistryImpl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setConvertToUpperCase(true);
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildRegistry());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(allowedOrigin);
        configuration.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
        configuration.setAllowedHeaders(Arrays.asList(ALLOWED_HEADERS));
        //        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    Keycloak Keycloak () {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .grantType(OAuth2Constants.PASSWORD)
                .realm(properties.getRealm())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .clientId(properties.getClientId())
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }
}
