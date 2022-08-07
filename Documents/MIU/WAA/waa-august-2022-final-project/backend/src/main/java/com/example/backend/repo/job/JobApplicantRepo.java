package com.example.backend.repo.job;

import com.example.backend.domain.job.JobApplicant;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicantRepo extends SoftDeleteJpaRepository<JobApplicant> {

    List<JobApplicant> findAllByJobAdvertisementId(Long adId);

    Optional<JobApplicant> findByJobAdvertisementIdAndStudentId(Long adId, Long studentId);
}
