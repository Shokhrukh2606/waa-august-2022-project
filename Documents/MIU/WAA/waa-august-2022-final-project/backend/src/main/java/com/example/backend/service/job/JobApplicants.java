package com.example.backend.service.job;

import com.example.backend.dto.job.JobApplicantDto;
import com.example.backend.dto.user.StudentDto;

import java.util.List;

public interface JobApplicants {

    List<StudentDto> getAllByJobAdvertisementId(Long adId);

    JobApplicantDto applyToJob(Long adId);
}
