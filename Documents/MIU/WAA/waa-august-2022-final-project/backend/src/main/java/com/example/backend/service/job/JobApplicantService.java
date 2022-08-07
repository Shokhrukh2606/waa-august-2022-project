package com.example.backend.service.job;

import com.example.backend.domain.job.JobApplicant;
import com.example.backend.dto.job.JobApplicantDto;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.JobApplicantMapper;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.repo.job.JobAdvertisementRepo;
import com.example.backend.repo.job.JobApplicantRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JobApplicantService implements JobApplicants {

    private final JobApplicantRepo repo;
    private final JobAdvertisementRepo jobAdvertisementRepo;

    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final Security security;

    private final JobApplicantMapper mapper;

    @Override
    public List<StudentDto> getAllByJobAdvertisementId(Long adId) {
        var advertisement = jobAdvertisementRepo.findById(adId)
                .orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, "JobAdvertisements ID"));

        if (!advertisement.getCreator().getId().equals(security.getCurrentUser().getId())) {
            throw new LocalizedApplicationException(ErrorCode.FORBIDDEN, "This information can be reached only by creators of the Advertisement");
        }

        var applicants = repo.findAllByJobAdvertisementId(adId);

        return applicants.stream().map(JobApplicant::getStudent).map(studentMapper::toDtoWithCV).collect(Collectors.toList());
    }

    @Override
    public JobApplicantDto applyToJob(Long adId) {
        var advertisement = jobAdvertisementRepo.findById(adId)
                .orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, "JobAdvertisements ID"));

        if (repo.findByJobAdvertisementIdAndStudentId(adId, security.getCurrentUser().getId()).isPresent()) {
            throw new EntityExistsException("You already have applied to this Job");
        }

        var student = studentRepo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new);

        return mapper.toDtoForAdvertisement(repo.save(new JobApplicant(advertisement, student)));
    }
}
