package com.example.backend.repo.job;

import com.example.backend.domain.job.JobHistory;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobHistoryRepo extends SoftDeleteJpaRepository<JobHistory> {

    List<JobHistory> findAllByStudentId(Long studentId);
}
