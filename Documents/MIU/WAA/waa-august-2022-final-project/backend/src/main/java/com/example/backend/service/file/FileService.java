package com.example.backend.service.file;

import com.example.backend.domain.file.ResourceFile;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.property.FileStorageProperties;
import com.example.backend.repo.file.FileRepo;
import com.example.backend.utils.BaseUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;

import static com.example.backend.controller.file.FileStorageController.PATH;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;

@Service
public class FileService implements Files {

    private final Path rootLocation;
    private final BaseUtils utils;
    private final FileRepo fileRepo;

    public FileService(BaseUtils utils, FileRepo fileRepo, FileStorageProperties properties) {
        this.utils = utils;
        this.fileRepo = fileRepo;
        this.rootLocation = Paths.get(properties.getUploadDir());
    }

    @PostConstruct
    public void init() {
        try {
            createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException(ErrorCode.COULD_NOT_INITIALIZE_DIRECTION.name(), e);
        }
    }

    @Transactional
    @Override
    public ResourceFile storeFile(MultipartFile file) {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (file.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.FILE_EMPTY);
        }

        if (filename.contains("..")) {
            throw new LocalizedApplicationException(ErrorCode.FILED_STORE_EMPTY_FILE);
        }

        ResourceFile original;

        String fileNamePrefix = Objects.requireNonNull(StringUtils.split(filename, "."))[0];
        String fileExtension = StringUtils.getFilenameExtension(filename);
        String fileSuffix = utils.encodeToMd5(fileNamePrefix) + new Date().getTime();

        try {
            String newFileName = fileSuffix + "." + fileExtension;
            saveFile(file, newFileName);
            original = saveResourceFile(filename, newFileName, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return original;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.name());
            }
        } catch (Exception ex) {
            throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.name());
        }
    }

    @Transactional
    @Override
    public void removeFile(Long id) {
        try {
            var resourceFile = fileRepo.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND));
            String original = resourceFile.getUrl();
            var url = resourceFile.getUrl().replace("/api" + PATH + "/", "");
            Path filePath = rootLocation.resolve(url).normalize();
            File file = new File(String.valueOf(filePath));
            if (file.exists()) {
                java.nio.file.Files.delete(filePath);
                fileRepo.delete(resourceFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResourceFile saveResourceFile(String fileName, String newFileName, MultipartFile file) {
        ResourceFile resourceFile = new ResourceFile();
        resourceFile.setName(fileName);
        resourceFile.setUrl("/api" + PATH + "/" + newFileName);
        resourceFile.setMimeType(file.getContentType());
        resourceFile.setSize(file.getSize());

        return fileRepo.save(resourceFile);
    }

    void saveFile(MultipartFile file, String fileName) throws IOException {
        copy(file.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    }
}
