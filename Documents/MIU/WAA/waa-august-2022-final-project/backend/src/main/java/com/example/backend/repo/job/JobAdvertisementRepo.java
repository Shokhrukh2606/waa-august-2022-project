package com.example.backend.repo.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAdvertisementRepo extends SoftDeleteJpaRepository<JobAdvertisement> {

}
