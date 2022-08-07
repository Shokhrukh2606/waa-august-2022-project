package com.example.backend.service.user;

import com.example.backend.domain.user.Faculty;
import com.example.backend.dto.user.FacultyDto;
import com.example.backend.dto.user.FacultyUpdateRequest;
import com.example.backend.mapper.FacultyMapper;
import com.example.backend.repo.user.FacultyRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class FacultyService implements Faculties {

    private final FacultyRepo repo;
    private final FacultyMapper mapper;
    private final Security security;

    @Override
    public FacultyDto updateProfile(FacultyUpdateRequest request) {

        Faculty faculty = repo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new);

        faculty.setDepartment(request.getDepartment());
        return mapper.toDto(repo.save(faculty));
    }
}
