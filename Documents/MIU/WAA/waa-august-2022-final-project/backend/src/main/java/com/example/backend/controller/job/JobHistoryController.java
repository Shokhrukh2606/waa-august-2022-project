package com.example.backend.controller.job;

import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;
import com.example.backend.service.job.JobHistories;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@ApiModel("Endpoint for handling job history of students")
public class JobHistoryController {

    private final JobHistories jobHistories;

    @PostMapping("/students/job-histories")
    @ApiOperation(value="Creating a job history of a student", response= JobHistoryDto.class)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public JobHistoryDto create(@Valid @RequestBody JobHistoryCreateRequest request) {
        return jobHistories.create(request);
    }

    @PutMapping("/students/job-histories/{id}")
    @ApiOperation(value="Updating a job history of a student", response= JobHistoryDto.class)
    public JobHistoryDto update(@Valid @PathVariable("id") Long jobHistoryId, @RequestBody JobHistoryCreateRequest request){
        return jobHistories.update(jobHistoryId, request);
    }

    @DeleteMapping("/students/job-histories/{id}")
    @ApiOperation(value = "Deleting a job history")
    public void delete(@ApiParam(value = "Job history id", required = true) @PathVariable("id") Long jobHistoryId) {
        jobHistories.delete(jobHistoryId);
    }

    //todo only faculty member can do this
    @GetMapping("/students/{id}/job-histories")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @ApiOperation(value="Getting a list of jobs that a student has worked at", response= JobHistoryDto.class, responseContainer = "List")
    public List<JobHistoryDto> getByStudentId(@PathVariable("id") Long studentId) {
        return jobHistories.getHistoryByStudentId(studentId);
    }
}
