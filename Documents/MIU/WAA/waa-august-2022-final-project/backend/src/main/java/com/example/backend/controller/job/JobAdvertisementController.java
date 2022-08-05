package com.example.backend.controller.job;

import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.job.JobAdvertisements;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/advertisements")
public class JobAdvertisementController {


    private final JobAdvertisements advertisements;

    @GetMapping("/filter")
    public Page<JobAdvertisementDto> search(@Valid JobAdvertisementSearch search){
        return advertisements.search(search);
    }

}
