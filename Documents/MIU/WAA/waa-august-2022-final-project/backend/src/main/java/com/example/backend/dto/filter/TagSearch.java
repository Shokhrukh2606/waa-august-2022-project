package com.example.backend.dto.filter;

import com.example.backend.dto.AbstractRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TagSearch extends PageableSearch implements AbstractRequest {

    @NotBlank
    private String title;

}
