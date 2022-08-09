package com.example.backend.controller.job;

import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.dto.job.JobApplicantDto;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.service.job.JobAdvertisements;
import com.example.backend.service.job.JobApplicants;
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

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/advertisements")
@ApiModel("Endpoint for handling Job advertisement")
@Slf4j
public class JobAdvertisementController {

    private final JobAdvertisements advertisements;
    private final JobApplicants jobApplicants;

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    @ApiOperation(value = "Getting the list of Job advertisements", response = JobAdvertisementDto.class, responseContainer = "List")
    public Page<JobAdvertisementDto> search(@ApiParam("Request list") @Valid JobAdvertisementSearch search) {

        log.info("Accessing GET api/advertisements/filter {}", search);

        var result = advertisements.search(search);

        log.info("{} advertisements were found", result.getTotalElements());

        return result;
    }

    @PostMapping
    @ApiOperation(value = "Creating a job advertisement", response = JobAdvertisementDto.class)
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public JobAdvertisementDto create(@ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto) {

        log.info("Accessing POST api/advertisements {}", dto);

        var result = advertisements.create(dto);

        log.info("{} advertisements was created", result);

        return result;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updating a job advertisement", response = JobAdvertisementDto.class)
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public JobAdvertisementDto update(
            @ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Request body", required = true) @Valid @RequestBody JobAdvertisementCreateRequestDto dto
    ) {
        log.info("Accessing PUT api/advertisements/{} {}", id, dto);

        var result = advertisements.update(id, dto);

        log.info("{} advertisement was updated", result);

        return result;
    }

    @ApiOperation(value = "Deleting a job advertisement")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public void delete(@ApiParam(value = "Job advertisement id", required = true) @PathVariable("id") Long id) {

        log.info("Accessing DELETE api/advertisements/{}", id);

        advertisements.delete(id);

        log.info("Advertisement was deleted");
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Applying to a job in a job advertisement")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public JobApplicantDto apply(@PathVariable("id") Long id) {

        log.info("Accessing POST api/advertisements/{}", id);

        var result = jobApplicants.applyToJob(id);

        log.info("{} was created", result);

        return result;
    }

    @GetMapping("/{id}/applicants")
    @ApiOperation(value = "Getting all the applicants for a particular job advertisement")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public List<StudentDto> getApplicants(@PathVariable("id") Long id) {

        log.info("Accessing GET api/advertisements/{}/applicants", id);

        var result = jobApplicants.getAllByJobAdvertisementId(id);

        log.info("{} applicants were retrieved for a job advertisement", result);

        return result;
    }

    @GetMapping("/recently-applied")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_FACULTY')")
    public List<JobAdvertisementDto> getRecentlyAppliedAds(){
        return advertisements.getRecentlyAppliedAds();
    }
}
