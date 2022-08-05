package com.example.backend.service.file;

import com.example.backend.domain.file.ResourceFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface Files {

    ResourceFile storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);

    void removeFile(String url);

}
