package com.example.backend.controller.user;

import com.example.backend.dto.user.FacultyDto;
import com.example.backend.dto.user.FacultyUpdateRequest;
import com.example.backend.service.user.Faculties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/faculties")
@ApiModel("Endpoint for handling faculties")
@Slf4j
public class FacultyController {

    private final Faculties faculties;

    @PutMapping
    @ApiOperation(value = "Updating a faculty profile", response = FacultyDto.class)
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public FacultyDto update(@Valid @RequestBody FacultyUpdateRequest request) {

        log.info("Accessing PUT api/faculties {}", request);

        var result = faculties.updateProfile(request);

        log.info("{} faculty profile was updated", result);

        return result;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public FacultyDto getAuthorizedFaculty() {

        log.info("Accessing GET api/faculties/me");

        var result = faculties.getAuthorizedFaculty();

        log.info("{} current authorized faculty was retrieved", result);

        return result;
    }
}
