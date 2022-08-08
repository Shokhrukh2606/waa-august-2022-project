package com.example.backend.dto.filter;

import com.example.backend.domain.State;
import com.example.backend.domain.user.Major;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentSearch extends PageableSearch {

    private Long id;

    private State state;

    private String city;

    private Major major;

    private String name;
}
