package com.example.backend.service.job;

import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobAdvertisements {

    JobAdvertisementDto create(JobAdvertisementCreateRequestDto dto);
    JobAdvertisementDto update(Long id, JobAdvertisementCreateRequestDto dto);
    Page<JobAdvertisementDto> search(JobAdvertisementSearch search);

    List<JobAdvertisementDto> getRecentlyAppliedAds();

    void delete(Long id);
}
