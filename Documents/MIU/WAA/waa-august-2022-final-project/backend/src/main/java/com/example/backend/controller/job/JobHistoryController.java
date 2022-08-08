package com.example.backend.controller.job;

import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;
import com.example.backend.service.job.JobHistories;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@ApiModel("Endpoint for handling job history of students")
@Slf4j
public class JobHistoryController {

    private final JobHistories jobHistories;

    @PostMapping("/students/job-histories")
    @ApiOperation(value = "Creating a job history of a student", response = JobHistoryDto.class)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public JobHistoryDto create(@Valid @RequestBody JobHistoryCreateRequest request) {

        log.info("Accessing POST api/students/job-histories {}", request);

        var result = jobHistories.create(request);

        log.info("{} job history was created", result);

        return result;
    }

    @PutMapping("/students/job-histories/{id}")
    @ApiOperation(value = "Updating a job history of a student", response = JobHistoryDto.class)
    public JobHistoryDto update(@Valid @PathVariable("id") Long jobHistoryId, @RequestBody JobHistoryCreateRequest request) {

        log.info("Accessing PUT api/students/job-histories/{} {}", jobHistoryId, request);

        var result = jobHistories.update(jobHistoryId, request);

        log.info("{} job history was updated", result);

        return result;
    }

    @DeleteMapping("/students/job-histories/{id}")
    @ApiOperation(value = "Deleting a job history")
    public void delete(@ApiParam(value = "Job history id", required = true) @PathVariable("id") Long jobHistoryId) {

        log.info("Accessing DELETE api/students/job-histories/{}", jobHistoryId);

        jobHistories.delete(jobHistoryId);

        log.info("Job history was deleted");
    }

    //todo only faculty member can do this
    @GetMapping("/students/{id}/job-histories")
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    @ApiOperation(value = "Getting a list of jobs that a student has worked at", response = JobHistoryDto.class, responseContainer = "List")
    public List<JobHistoryDto> getByStudentId(@PathVariable("id") Long studentId) {

        log.info("Accessing GET api/students/{}/job-histories", studentId);

        var result = jobHistories.getHistoryByStudentId(studentId);

        log.info("{} job history list was retrieved", result);

        return result;
    }
}
