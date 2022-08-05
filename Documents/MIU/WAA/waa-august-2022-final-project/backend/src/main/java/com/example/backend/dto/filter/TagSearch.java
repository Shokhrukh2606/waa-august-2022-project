package com.example.backend.dto.filter;

import com.example.backend.dto.AbstractRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagSearch extends PageableSearch implements AbstractRequest {

    private String title;

}
