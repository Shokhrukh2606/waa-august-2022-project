package com.example.backend.service.user;

import com.example.backend.domain.user.Faculty;
import com.example.backend.dto.user.FacultyDto;
import com.example.backend.dto.user.FacultyUpdateRequest;
import com.example.backend.mapper.FacultyMapper;
import com.example.backend.repo.user.FacultyRepo;
import com.example.backend.service.security.Security;
import com.example.backend.utils.KeyCloakUtils;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class FacultyService implements Faculties {

    private final FacultyRepo repo;
    private final FacultyMapper mapper;
    private final Security security;

    private final KeyCloakUtils keyCloakUtils;

    @Override
    public FacultyDto updateProfile(FacultyUpdateRequest request) {

        Faculty faculty = repo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new);

        faculty.setDepartment(request.getDepartment());

        if (!ObjectUtils.isEmpty(request.getFirstname())) {
            faculty.setFirstname(request.getFirstname());
        }

        if (!ObjectUtils.isEmpty(request.getLastname())) {
            faculty.setLastname(request.getLastname());
        }

        if (!ObjectUtils.isEmpty(request.getEmail())) {
            faculty.setEmail(request.getEmail());
        }

        var userResource = keyCloakUtils.getUserResource(faculty.getKeyClockUserId());
        var userRepresentation = keyCloakUtils.getUserRepresentation(userResource);



        if (!ObjectUtils.isEmpty(request.getFirstname())) {
            userRepresentation.setFirstName(request.getFirstname());
        }

        if (!ObjectUtils.isEmpty(request.getLastname())) {
            userRepresentation.setLastName(request.getLastname());
        }

        if (!ObjectUtils.isEmpty(request.getEmail())) {
            userRepresentation.setEmail(request.getEmail());
        }

        return mapper.toDto(repo.save(faculty));
    }

    @Override
    public FacultyDto getAuthorizedStudent() {
        return mapper.toDto(repo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new));
    }
}
