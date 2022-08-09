package com.example.backend.dto.user;

import com.example.backend.domain.user.Department;
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
public class FacultyUpdateRequest {

    private String firstname;

    private String lastname;

    private String email;

    private Department department;
}
