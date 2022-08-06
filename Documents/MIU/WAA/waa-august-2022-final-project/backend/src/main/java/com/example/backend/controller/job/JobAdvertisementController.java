package com.example.backend.controller.job;

import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.job.JobAdvertisements;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/advertisements")
@ApiModel("Endpoint for handling Job advertisement")
public class JobAdvertisementController {

    private final JobAdvertisements advertisements;

    @GetMapping("/filter")
    @ApiOperation(value = "Getting the list of Job advertisements", response = JobAdvertisementDto.class, responseContainer = "List")
    public Page<JobAdvertisementDto> search(@ApiParam("Request list") @Valid JobAdvertisementSearch search) {
        return advertisements.search(search);
    }

    @PostMapping
    public JobAdvertisementDto create(@Valid @RequestBody JobAdvertisementCreateRequestDto dto) {
        return advertisements.create(dto);
    }
}
