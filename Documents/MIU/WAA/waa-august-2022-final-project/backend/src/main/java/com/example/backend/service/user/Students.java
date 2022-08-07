package com.example.backend.service.user;

import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.dto.user.StudentUpdateRequest;
import org.springframework.data.domain.Page;

public interface Students {


    Page<StudentDto> search(StudentSearch search);

    StudentDto updateProfile(StudentUpdateRequest request);
}
