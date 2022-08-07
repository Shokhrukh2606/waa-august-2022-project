package com.example.backend.service.user;

import com.example.backend.dto.user.CommentOnStudentCreateRequest;
import com.example.backend.dto.user.CommentOnStudentDto;

import java.util.List;

public interface CommentOnStudents {

    CommentOnStudentDto create(CommentOnStudentCreateRequest request);

    List<CommentOnStudentDto> getAllByStudentId(Long id);
}
