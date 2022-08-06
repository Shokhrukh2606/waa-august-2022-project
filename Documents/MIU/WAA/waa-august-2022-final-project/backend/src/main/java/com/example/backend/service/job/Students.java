package com.example.backend.service.job;

import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.user.StudentDto;
import org.springframework.data.domain.Page;

public interface Students {


    Page<StudentDto> search(StudentSearch search);
}
