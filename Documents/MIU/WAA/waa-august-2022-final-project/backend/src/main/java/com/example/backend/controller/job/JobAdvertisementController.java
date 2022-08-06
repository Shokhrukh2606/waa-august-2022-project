package com.example.backend.controller.job;

import com.example.backend.dto.file.ResourceFileDto;
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
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/advertisements")
@ApiModel("Endpoint for handling Job advertisement")
@Slf4j
public class JobAdvertisementController {

    private final JobAdvertisements advertisements;

    @GetMapping("/filter")
    @ApiOperation(value = "Getting the list of Job advertisements", response = JobAdvertisementDto.class, responseContainer = "List")
    public Page<JobAdvertisementDto> search(@ApiParam("Request list") @Valid JobAdvertisementSearch search) {
        return advertisements.search(search);
    }

    @PostMapping
    @ApiOperation(value = "Creating a job advertisement", response = JobAdvertisementDto.class)
    public JobAdvertisementDto create(@ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto) {
        return advertisements.create(dto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updating a job advertisement", response = JobAdvertisementDto.class)
    public JobAdvertisementDto update(@ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id, @ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto) {
        return advertisements.update(id, dto);
    }

    @ApiOperation(value = "Deleting a job advertisement")
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id) {
        advertisements.delete(id);
    }
}
