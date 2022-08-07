package com.example.backend.mapper;

import com.example.backend.domain.user.Student;
import com.example.backend.dto.user.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student student);

    @Named("toDtoWithCV")
    StudentDto toDtoWithCV(Student student);
}
