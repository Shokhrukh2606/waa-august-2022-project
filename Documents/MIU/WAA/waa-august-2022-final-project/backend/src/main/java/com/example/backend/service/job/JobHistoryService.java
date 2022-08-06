package com.example.backend.service.job;

import com.example.backend.domain.job.JobHistory;
import com.example.backend.domain.job.Tag;
import com.example.backend.dto.job.JobHistoryCreateRequest;
import com.example.backend.dto.job.JobHistoryDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.JobHistoryMapper;
import com.example.backend.repo.job.JobHistoryRepo;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobHistoryService implements JobHistories {

    private final JobHistoryRepo repo;
    private final JobHistoryMapper mapper;
    private final TagRepo tagRepo;

    private final Security security;

    private final StudentRepo studentRepo;

    @Override
    public JobHistoryDto create(JobHistoryCreateRequest request) {
        if (ObjectUtils.isEmpty(request.getCompanyName())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "companyName");
        }

        if (ObjectUtils.isEmpty(request.getStartDate())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "startDate");
        }

        JobHistory history = mapper.fromCreate(request);

        tagRepo.saveAll(request.getTags().stream().filter(item -> tagRepo.findByTitle(item).isEmpty()).map(Tag::new).collect(Collectors.toList()));

        var tags = tagRepo.findAllByTitleIsIn(request.getTags());//move the same logic to tags service

        history.setTags(tags);
        history.setStudent(studentRepo.findById(security.getCurrentUser().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cannot find student with id: %s", security.getCurrentUser().getId()))));

        return mapper.toDto(repo.save(history));
    }

    @Override
    public List<JobHistoryDto> getHistoryByStudentId(Long studentId) {
        return repo.findAllByStudentId(studentId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
