package com.example.backend.service.job;

import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;

import java.util.List;

public interface JobHistories {

    JobHistoryDto create(JobHistoryCreateRequest request);

    JobHistoryDto update(Long id, JobHistoryCreateRequest request);

    void delete(Long id);

    List<JobHistoryDto> getHistoryByStudentId(Long studentId);
}
