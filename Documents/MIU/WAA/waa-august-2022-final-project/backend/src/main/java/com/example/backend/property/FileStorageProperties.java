package com.example.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file-storage.prop")
public class FileStorageProperties {

    private String uploadDir;
}
