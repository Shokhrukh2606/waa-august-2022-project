package com.example.backend.repo.user;

import com.example.backend.domain.user.Student;
import com.example.backend.repo.SoftDeleteJpaRepository;

public interface StudentRepo extends SoftDeleteJpaRepository<Student> {

}
