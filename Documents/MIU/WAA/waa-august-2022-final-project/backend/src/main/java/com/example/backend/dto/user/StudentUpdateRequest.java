package com.example.backend.dto.user;

import com.example.backend.domain.job.City;
import com.example.backend.domain.user.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentUpdateRequest {

    private Float gpa;

    private Major major;

    private City city;

    private String cvUrl;

}
