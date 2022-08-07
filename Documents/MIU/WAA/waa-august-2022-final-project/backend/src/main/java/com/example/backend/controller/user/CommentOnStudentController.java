package com.example.backend.controller.user;

import com.example.backend.dto.user.CommentOnStudentCreateRequest;
import com.example.backend.dto.user.CommentOnStudentDto;
import com.example.backend.service.user.CommentOnStudents;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/comments")
public class CommentOnStudentController {

    private final CommentOnStudents commentOnStudents;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public CommentOnStudentDto create(@Valid @RequestBody CommentOnStudentCreateRequest request) {
        return commentOnStudents.create(request);
    }
}
