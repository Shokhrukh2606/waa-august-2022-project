package com.example.backend.repo.file;

import com.example.backend.domain.file.ResourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepo extends JpaRepository<ResourceFile, Long> {

     Optional<ResourceFile> findByUrl(String url);
}
