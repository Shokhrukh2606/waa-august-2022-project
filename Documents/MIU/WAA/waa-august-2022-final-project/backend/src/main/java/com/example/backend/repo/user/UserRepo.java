package com.example.backend.repo.user;

import com.example.backend.domain.user.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<LocalUser, Long> {

    Optional<LocalUser> findFirstByEmail(String email);
}
