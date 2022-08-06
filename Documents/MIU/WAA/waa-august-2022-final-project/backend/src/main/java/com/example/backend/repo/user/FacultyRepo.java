package com.example.backend.repo.user;

import com.example.backend.domain.user.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepo extends JpaRepository<Faculty, Long> {

}
