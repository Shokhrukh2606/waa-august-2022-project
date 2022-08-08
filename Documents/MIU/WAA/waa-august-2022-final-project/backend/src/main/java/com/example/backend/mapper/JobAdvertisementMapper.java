package com.example.backend.mapper;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface JobAdvertisementMapper {

    @Mapping(target = "tags", ignore = true)
    JobAdvertisement fromCreate(JobAdvertisementCreateRequestDto dto);

    @Mapping(source = "creator.keyClockUserId", target = "creatorId")
    JobAdvertisementDto toDto(JobAdvertisement advertisement);

}

