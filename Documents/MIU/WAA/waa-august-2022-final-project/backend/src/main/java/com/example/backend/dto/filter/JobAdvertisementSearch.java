package com.example.backend.dto.filter;

import com.example.backend.domain.State;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ApiModel("Job Advertisement Search Dto")
@ToString
public class JobAdvertisementSearch extends PageableSearch {

    private List<String> tags;

    private State state;

    private String city;

    private String companyName;
}
