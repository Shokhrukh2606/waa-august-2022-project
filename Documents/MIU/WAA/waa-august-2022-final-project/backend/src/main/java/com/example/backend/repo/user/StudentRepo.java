package com.example.backend.repo.user;

import com.example.backend.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
