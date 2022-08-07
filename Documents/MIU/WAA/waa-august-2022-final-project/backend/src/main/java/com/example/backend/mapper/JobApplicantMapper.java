package com.example.backend.mapper;

import com.example.backend.domain.job.JobApplicant;
import com.example.backend.dto.job.JobApplicantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface JobApplicantMapper {

    JobApplicantDto toDtoForAdvertisement(JobApplicant applicant);
}
