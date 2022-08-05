package com.example.backend.repo.job;

import com.example.backend.domain.job.Tag;
import com.example.backend.repo.SoftDeleteJpaRepository;

import java.util.Optional;

public interface TagRepo extends SoftDeleteJpaRepository<Tag> {

    Optional<Tag> findByTitle(String title);
}
