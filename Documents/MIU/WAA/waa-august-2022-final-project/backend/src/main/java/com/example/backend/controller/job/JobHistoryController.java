package com.example.backend.controller.job;

import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;
import com.example.backend.service.job.JobHistories;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class JobHistoryController {

    private final JobHistories jobHistories;

    @PostMapping("/students/job-histories")
    public JobHistoryDto create(@Valid @RequestBody JobHistoryCreateRequest request) {
        return jobHistories.create(request);
    }

    //todo only faculty member can do this
    @GetMapping("/students/{id}/job-histories")
    public List<JobHistoryDto> getByStudentId(@PathVariable("id") Long studentId) {
        return jobHistories.getHistoryByStudentId(studentId);
    }
}
