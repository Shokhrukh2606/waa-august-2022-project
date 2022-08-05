package com.example.backend.mapper;

import com.example.backend.domain.file.ResourceFile;
import com.example.backend.dto.file.ResourceFileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    ResourceFile fromDto(ResourceFileDto file);

    ResourceFileDto toDto(ResourceFile file);
}
