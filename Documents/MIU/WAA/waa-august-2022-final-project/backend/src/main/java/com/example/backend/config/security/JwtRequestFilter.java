package com.example.backend.config.security;

import com.example.backend.dto.ErrorDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.repo.user.UserRepo;
import com.example.backend.service.security.Security;
import com.example.backend.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String[] AUTH_WHITELIST = {"/swagger-resources/**", "/v2/api-docs/**", "/swagger.json",
            "/swagger-ui.html", "/webjars/**", "/api/resource/uploads/**", "/api/users", "/api/students", "/api/faculties"};

    private static final String CREATE_PROFILE_URL = "http://localhost:3000/users/create";
    private static final String UPDATE_PROFILE_URL = "http://localhost:3000/users/update";

    private final Security security;
    private final UserRepo userRepo;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var user = security.getCurrentUser();

            if (!user.isUpdated()) {
                makeErrorBody(response, ErrorCode.UPDATE_PROFILE_REQUIRED);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (UsernameNotFoundException ignore) {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                throw new UsernameNotFoundException("Should be authenticated");
            }

            var authUser = JwtUtils.getUserFromToken(authentication);
            var dbUser = userRepo.findFirstByEmail(authUser.getEmail());

            if (dbUser.isEmpty()) {
                makeErrorBody(response, ErrorCode.CREATE_PROFILE_REQUIRED);
            }
        }
    }

    private void makeErrorBody(HttpServletResponse response, ErrorCode code) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(new ErrorDto(code.name(), List.of())));
        out.flush();
        response.setStatus(400);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(AUTH_WHITELIST)
                .anyMatch(p -> switch (p) {
                    case "/api/users", "/api/students", "/api/faculties" -> pathMatcher.match(p, request.getServletPath()) &&
                                                                            (request.getMethod().equals(HttpMethod.POST.name()) ||
                                                                             request.getMethod().equals(HttpMethod.PUT.name()));
                    default -> pathMatcher.match(p, request.getServletPath());
                });
    }
}
