package com.example.backend.controller.user;

import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.user.CommentOnStudentDto;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.dto.user.StudentUpdateRequest;
import com.example.backend.service.user.CommentOnStudents;
import com.example.backend.service.user.Students;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/students")
@ApiModel("Endpoint for handling students")
@Slf4j
public class StudentsController {

    private final Students students;
    private final CommentOnStudents commentOnStudents;

    @GetMapping("/filter")
    @ApiOperation(value = "Searching for students", response = StudentDto.class, responseContainer = "List")
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    public Page<StudentDto> search(@Valid StudentSearch search) {

        log.info("Accessing GET api/students/filter {}", search);

        var result = students.search(search);

        log.info("{} list of students was retrieved", result);

        return result;
    }

    @PutMapping
    @ApiOperation(value = "Updating a student profile", response = StudentDto.class)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public StudentDto update(@Valid @RequestBody StudentUpdateRequest request) {

        log.info("Accessing PUT api/students {}", request);

        var result = students.updateProfile(request);

        log.info("{} student profile was updated", result);

        return result;
    }

    @GetMapping("/{id}/comments")
    @PreAuthorize("hasRole('ROLE_FACULTY')")
    @ApiOperation(value = "Getting the list of comments about a student", response = CommentOnStudentDto.class, responseContainer = "List")
    public List<CommentOnStudentDto> getCommentsByStudent(@PathVariable("id") Long id) {

        log.info("Accessing GET api/students/{}/comments", id);

        var result = commentOnStudents.getAllByStudentId(id);

        log.info("{} list of comments was retrieved", result);

        return result;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public StudentDto getAuthorizedStudent() {

        log.info("Accessing GET api/students/me");

        var result = students.getAuthorizedStudent();

        log.info("{} current authorized student was retrieved", result);

        return result;
    }
}
