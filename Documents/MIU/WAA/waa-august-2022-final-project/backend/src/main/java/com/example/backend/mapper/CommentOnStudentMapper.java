package com.example.backend.mapper;

import com.example.backend.domain.user.CommentOnStudent;
import com.example.backend.dto.user.CommentOnStudentCreateRequest;
import com.example.backend.dto.user.CommentOnStudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface CommentOnStudentMapper {

    CommentOnStudent fromCreate(CommentOnStudentCreateRequest request);

    CommentOnStudentDto toDto(CommentOnStudent comment);

    @Mapping(target = "student", ignore = true)
    CommentOnStudentDto listByStudent(CommentOnStudent comment);
}
