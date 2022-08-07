package com.example.backend.controller.user;

import com.example.backend.dto.user.FacultyDto;
import com.example.backend.dto.user.FacultyUpdateRequest;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.dto.user.StudentUpdateRequest;
import com.example.backend.service.user.Faculties;
import com.example.backend.service.user.FacultyService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/faculties")
@ApiModel("Endpoint for handling faculties")
public class FacultyController {

    private final Faculties faculties;

    @PutMapping
    @ApiOperation(value = "Updating a faculty profile", response = FacultyDto.class)
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public FacultyDto update(@Valid @RequestBody FacultyUpdateRequest request){
        return faculties.updateProfile(request);
    }
}
