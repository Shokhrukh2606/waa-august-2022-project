package com.example.backend.repo.user;

import com.example.backend.domain.user.Student;
import com.example.backend.repo.SoftDeleteJpaRepository;

import java.util.Optional;

public interface StudentRepo extends SoftDeleteJpaRepository<Student> {

    Optional<Student> findByEmail(String email);

}
