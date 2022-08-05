package com.example.backend.service.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.domain.job.Tag;
import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.JobAdvertisementMapper;
import com.example.backend.repo.file.JobAdvertisementRepo;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JobAdvertisementService implements JobAdvertisements {

    private final JobAdvertisementRepo repo;
    private final JobAdvertisementMapper mapper;
    private final TagRepo tagRepo;

    private final Security security;

    @Override
    public JobAdvertisementDto create(JobAdvertisementCreateRequestDto dto) {
        JobAdvertisement advertisement = mapper.fromCreate(dto);

        if (ObjectUtils.isEmpty(dto.getCompanyName())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "companyName");
        }

        if (ObjectUtils.isEmpty(dto.getCity())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "city");
        }

        if (ObjectUtils.isEmpty(dto.getCity())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "description");
        }

        var tags = tagRepo.saveAll(
                dto.getTags().stream()
                        .filter(item -> tagRepo.findByTitle(item).isEmpty())
                        .map(Tag::new)
                        .collect(Collectors.toList())
        );

        advertisement.setTags(tags);
        advertisement.setCreator(security.getCurrentUser());

        return mapper.toDto(repo.save(advertisement));
    }

    @Override
    public Page<JobAdvertisementDto> search(JobAdvertisementSearch search) {
        return null;
    }
}
