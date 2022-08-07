package com.example.backend.dto.user;

import com.example.backend.domain.job.City;
import com.example.backend.domain.user.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private String email;

    private String firstname;

    private String lastname;

    private Float gpa;

    private Major major;

    private City city;

    private String cvUrl;
}
