package com.example.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@ApiModel(description = "")
public record ErrorDto(@ApiModelProperty(value = "", example = "error.validation_error") String code, @ApiModelProperty(value = "") List<Serializable> params) {

}
