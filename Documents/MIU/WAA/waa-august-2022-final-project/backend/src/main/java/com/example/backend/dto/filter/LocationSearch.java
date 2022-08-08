package com.example.backend.dto.filter;

import com.example.backend.domain.State;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LocationSearch {

    private String city;

    @NotNull
    private State state;

}
