package com.example.backend.config.security;

import com.example.backend.domain.user.Faculty;
import com.example.backend.domain.user.Student;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.repo.user.FacultyRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String STUDENT_UPDATE_PROFILE_URL = "http://localhost:3000/student/%s/update";
    private static final String FACULTY_UPDATE_PROFILE_URL = "http://localhost:3000/faculty/%s/update";

    private final Security security;
    private final StudentRepo studentRepo;
    private final FacultyRepo facultyRepo;

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            security.getCurrentUser();
        } catch (UsernameNotFoundException ignore) {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                throw new UsernameNotFoundException("Should be authenticated");
            }
            var isStudent = false;

            var context = (RefreshableKeycloakSecurityContext) authentication.getCredentials();
            var token = context.getToken();

            var email = token.getEmail();
            var firstName = token.getGivenName();
            var lastName = token.getFamilyName();

            String redirectUrl;

            isStudent = authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().toLowerCase().contains("student"));

            if (isStudent) {
                Student student = new Student(email, firstName, lastName);
                student = studentRepo.save(student);

                if (ObjectUtils.isEmpty(student.getId())) {
                    throw new LocalizedApplicationException(ErrorCode.COULD_NOT_CREATE_ENTITY, student.toString());
                }

                redirectUrl = String.format(STUDENT_UPDATE_PROFILE_URL, student.getId());
            } else {
                Faculty faculty = new Faculty(email, firstName, lastName);
                faculty = facultyRepo.save(faculty);

                if (ObjectUtils.isEmpty(faculty.getId())) {
                    throw new LocalizedApplicationException(ErrorCode.COULD_NOT_CREATE_ENTITY, faculty.toString());
                }

                redirectUrl = String.format(FACULTY_UPDATE_PROFILE_URL, faculty.getId());
            }

            response.setHeader("Location", redirectUrl);
            response.setStatus(302);
        }

        filterChain.doFilter(request, response);
    }
}
