package com.example.backend.repo.user;

import com.example.backend.domain.user.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepo extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByEmail(String email);
}
