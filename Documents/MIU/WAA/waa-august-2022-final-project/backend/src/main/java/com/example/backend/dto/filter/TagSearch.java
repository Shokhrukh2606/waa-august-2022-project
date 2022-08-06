package com.example.backend.dto.filter;

import com.example.backend.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("Tag Search Dto")
public class TagSearch extends PageableSearch implements AbstractRequest {

    @NotBlank
    @ApiModelProperty(required = true)
    private String title;

}
