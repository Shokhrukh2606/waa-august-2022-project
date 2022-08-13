package com.example.backend.dto.filter;

import com.example.backend.domain.State;
import com.example.backend.domain.user.Major;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class StudentSearch extends PageableSearch {

    private Long id;

    private State state;

    private String city;

    private Major major;

    private String name;

    private List<String> tags;
}
