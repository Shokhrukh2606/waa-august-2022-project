package com.example.backend.controller.user;

import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.dto.user.StudentUpdateRequest;
import com.example.backend.service.user.Students;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/students")
@ApiModel("Endpoint for handling students")
public class StudentsController {

    private final Students students;

    @GetMapping("/filter")
    @ApiOperation(value = "Searching for students", response = StudentDto.class, responseContainer = "List")
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public Page<StudentDto> search(@Valid StudentSearch search) {
        return students.search(search);
    }

    @PutMapping
    @ApiOperation(value = "Updating a student profile", response = StudentDto.class)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public StudentDto update(@Valid @RequestBody StudentUpdateRequest request){
        return students.updateProfile(request);
    }
}
