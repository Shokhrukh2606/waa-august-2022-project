package com.example.backend.controller.user;

import com.example.backend.dto.chart.LocationCountDto;
import com.example.backend.dto.user.CommentOnStudentCreateRequest;
import com.example.backend.dto.user.CommentOnStudentDto;
import com.example.backend.service.user.CommentOnStudents;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/comments")
@ApiModel("Endpoint to handle comments by faculty about students")
@Slf4j
public class CommentOnStudentController {

    private final CommentOnStudents commentOnStudents;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    @ApiOperation(value = "Creating a comment by a faculty about a student", response = CommentOnStudentDto.class)
    public CommentOnStudentDto create(@Valid @RequestBody CommentOnStudentCreateRequest request) {

        log.info("Accessing POST api/comments {}", request);

        var result = commentOnStudents.create(request);

        log.info("{} comment about a student was created", result);

        return result;
    }
}
