package com.example.backend.mapper;

import com.example.backend.domain.job.JobHistory;
import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.example.backend.utils.TimeUtils.DATE_FORMAT;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface JobHistoryMapper {

    @Mapping(target = "tags", ignore = true)
    @Mapping(source = "startDate", target = "startDate", dateFormat = DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = DATE_FORMAT)
    JobHistory fromCreate(JobHistoryCreateRequest request);

    @Mapping(source = "startDate", target = "startDate", dateFormat = DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = DATE_FORMAT)
    JobHistory fromDto(JobHistoryDto dto);

    @Mapping(source = "startDate", target = "startDate", dateFormat = DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = DATE_FORMAT)
    JobHistoryDto toDto(JobHistory jobHistory);
}
