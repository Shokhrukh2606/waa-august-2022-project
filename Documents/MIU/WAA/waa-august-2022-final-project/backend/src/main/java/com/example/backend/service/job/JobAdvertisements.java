package com.example.backend.service.job;

import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;

public interface JobAdvertisements {

    JobAdvertisementDto create(JobAdvertisementCreateRequestDto dto);

}
