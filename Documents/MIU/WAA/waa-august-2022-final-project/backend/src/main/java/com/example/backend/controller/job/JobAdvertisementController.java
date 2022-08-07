package com.example.backend.controller.job;

import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.job.JobAdvertisements;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/advertisements")
@ApiModel("Endpoint for handling Job advertisement")
@Slf4j
public class JobAdvertisementController {

    private final JobAdvertisements advertisements;

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    @ApiOperation(value = "Getting the list of Job advertisements", response = JobAdvertisementDto.class, responseContainer = "List")
    public Page<JobAdvertisementDto> search(@ApiParam("Request list") @Valid JobAdvertisementSearch search) {
        return advertisements.search(search);
    }

    @PostMapping
    @ApiOperation(value = "Creating a job advertisement", response = JobAdvertisementDto.class)
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public JobAdvertisementDto create(@ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto) {
        return advertisements.create(dto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updating a job advertisement", response = JobAdvertisementDto.class)
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public JobAdvertisementDto update(
            @ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto
    ) {
        return advertisements.update(id, dto);
    }

    @ApiOperation(value = "Deleting a job advertisement")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public void delete(@ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id) {
        advertisements.delete(id);
    }
}
