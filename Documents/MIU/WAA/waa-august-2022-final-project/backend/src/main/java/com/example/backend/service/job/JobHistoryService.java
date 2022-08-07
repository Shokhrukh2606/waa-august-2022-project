package com.example.backend.service.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.domain.job.JobHistory;
import com.example.backend.domain.job.Tag;
import com.example.backend.domain.user.LocalUser;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
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
    public JobHistoryDto update(Long id, JobHistoryCreateRequest request) {
        LocalUser localUser = security.getCurrentUser();
        Optional<JobHistory> jobHistoryOptional = repo.findById(id);

        // Checks if jobHistory with specified id exists or not
        if (jobHistoryOptional.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND);
        }
        // Checks if jobHistory student is the same with logged in user
        if (!localUser.getId().equals(jobHistoryOptional.get().getStudent().getId())) {
            throw new AccessDeniedException(ErrorCode.FORBIDDEN + " Users can only update their own job history details");
        }

        JobHistory jobHistory = mapper.fromCreate(request);
        jobHistory.setId(jobHistoryOptional.get().getId());
        jobHistory.setStudent(jobHistoryOptional.get().getStudent());

        tagRepo.saveAll(request.getTags().stream().filter(item -> tagRepo.findByTitle(item).isEmpty()).map(Tag::new).collect(Collectors.toList()));
        var tags = tagRepo.findAllByTitleIsIn(request.getTags());//move the same logic to tags service

        jobHistory.setTags(tags);

        return mapper.toDto(repo.save(jobHistory));
    }

    @Override
    public void delete(Long id) {
        Optional<JobHistory> jobHistoryOptional = repo.findById(id);
        if (jobHistoryOptional.isPresent()) {
            JobHistory jobHistory = jobHistoryOptional.get();
            LocalUser localUser = security.getCurrentUser();
            if (localUser.getId().equals(jobHistory.getStudent().getId())) {
                repo.deleteById(id);
            } else {
                throw new AccessDeniedException(ErrorCode.FORBIDDEN + " Users can only delete their own job history");
            }
        }
    }

    @Override
    public List<JobHistoryDto> getHistoryByStudentId(Long studentId) {
        return repo.findAllByStudentId(studentId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
