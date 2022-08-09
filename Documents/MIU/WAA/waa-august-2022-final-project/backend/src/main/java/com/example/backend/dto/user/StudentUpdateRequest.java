package com.example.backend.dto.user;

import com.example.backend.domain.job.City;
import com.example.backend.domain.user.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentUpdateRequest {

    private String firstname;

    private String lastname;

    private String email;

    private Float gpa;

    private Major major;

    private City city;

    private String cvUrl;

    private List<String> tags = new ArrayList<>();

}
