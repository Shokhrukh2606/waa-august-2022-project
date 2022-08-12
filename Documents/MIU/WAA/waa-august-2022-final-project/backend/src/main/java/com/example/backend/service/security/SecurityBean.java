package com.example.backend.service.security;

import com.example.backend.domain.user.Faculty;
import com.example.backend.domain.user.LocalUser;
import com.example.backend.domain.user.Role;
import com.example.backend.domain.user.Student;
import com.example.backend.dto.user.LocalUserCreateRequest;
import com.example.backend.dto.user.LocalUserDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.mapper.UserMapper;
import com.example.backend.property.KeyCloakAdminProperties;
import com.example.backend.repo.user.FacultyRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.repo.user.UserRepo;
import com.example.backend.utils.JwtUtils;
import com.example.backend.utils.KeyCloakUtils;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityBean implements Security {

    private final UserMapper userMapper;
    private final StudentRepo studentRepo;
    private final FacultyRepo facultyRepo;
    private final UserRepo userRepo;

    private final KeyCloakUtils keyCloakUtils;

    @Override
    public LocalUser getCurrentUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UsernameNotFoundException("Should be authenticated");
        }

        var context = (RefreshableKeycloakSecurityContext) authentication.getCredentials();

        return loadUserByUsername(context.getToken().getEmail());
    }

    @Transactional
    @Override
    public LocalUserDto createAuthenticatedUser(LocalUserCreateRequest request) {

        LocalUser newUser = JwtUtils.getUserFromToken(SecurityContextHolder.getContext().getAuthentication());

        if (userRepo.findFirstByEmail(newUser.getEmail()).isPresent()) {
            throw new EntityExistsException(String.format("User with email: %s is already exist", newUser.getEmail()));
        }

        newUser.setRole(request.getRole());
        newUser.setFirebaseToken(request.getFirebaseToken());

        LocalUser dbUser;

        if (newUser.getRole() == Role.STUDENT) {
            dbUser = studentRepo.save(Student.createFromParent(newUser));
        } else {
            dbUser = facultyRepo.save(Faculty.createFromParent(newUser));
        }

        var userResource = keyCloakUtils.getUserResource(dbUser.getKeyClockUserId());
        userResource.roles().realmLevel().add(keyCloakUtils.roleToRealmRoleRepresentation(dbUser.getRole()));

        return userMapper.toDto(dbUser);
    }

    public LocalUser loadUserByUsername(String username) throws UsernameNotFoundException {
        var localUser = getUserByUserName(username);
        if (localUser.isPresent()) {
            return localUser.get();
        } else {
            throw new UsernameNotFoundException(ErrorCode.USERNAME_NOT_FOUND.name());
        }
    }

    private Optional<LocalUser> getUserByUserName(String username) {
        return userRepo.findFirstByEmail(username);
    }
}
