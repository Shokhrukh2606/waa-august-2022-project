package com.example.backend.service.security;

import com.example.backend.domain.user.LocalUser;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.repo.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityBean implements Security {

    private static LocalUser currentUser;

    private final UserRepo userRepo;

    @Override
    public LocalUser getCurrentUser() {
        if (currentUser != null) {
            return currentUser;
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UsernameNotFoundException("Should be authenticated");
        }

        return loadUserByUsername(authentication.getName());
    }

    public LocalUser loadUserByUsername(String username) throws UsernameNotFoundException {
        var localUser = getUserByUserName(username);
        if (localUser.isPresent()) {
            currentUser = localUser.get();
            return currentUser;
        } else {
            throw new UsernameNotFoundException(ErrorCode.USERNAME_NOT_FOUND.name());
        }
    }

    private Optional<LocalUser> getUserByUserName(String username) {
        return userRepo.findFirstByEmail(username);
    }
}
