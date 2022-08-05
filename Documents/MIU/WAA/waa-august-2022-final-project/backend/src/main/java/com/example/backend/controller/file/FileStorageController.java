package com.example.backend.controller.file;

import com.example.backend.domain.file.ResourceFile;
import com.example.backend.dto.file.ResourceFileDto;
import com.example.backend.mapper.FileMapper;
import com.example.backend.service.file.Files;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static com.example.backend.controller.file.FileStorageController.PATH;

@AllArgsConstructor
@RestController
@RequestMapping("api" + PATH)
public class FileStorageController {

    private final Files files;
    private final FileMapper fileMapper;

    private final Security security;
    public static final String PATH = "/resource/uploads";

    @PostMapping(produces = "application/json")
    public ResourceFileDto uploadFile(@RequestParam("file") MultipartFile file) {
        ResourceFile dbFile = files.storeFile(file);
        return fileMapper.toDto(dbFile);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = files.loadFileAsResource(fileName.replace("|", "/"));
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{url}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "url") String url) {
        files.removeFile(url);
        return ResponseEntity.ok(true);
    }
}
