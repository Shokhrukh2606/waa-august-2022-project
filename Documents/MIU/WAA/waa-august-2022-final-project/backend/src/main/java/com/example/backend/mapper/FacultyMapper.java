package com.example.backend.mapper;

import com.example.backend.domain.user.Faculty;
import com.example.backend.dto.user.FacultyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyDto toDto(Faculty faculty);
}
