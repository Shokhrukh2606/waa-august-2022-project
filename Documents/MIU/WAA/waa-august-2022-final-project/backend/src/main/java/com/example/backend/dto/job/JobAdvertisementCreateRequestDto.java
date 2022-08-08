package com.example.backend.dto.job;

import com.example.backend.domain.job.City;
import com.example.backend.dto.AbstractRequest;
import com.example.backend.dto.file.ResourceFileDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("Job Advertisement Create Request Dto")
public class JobAdvertisementCreateRequestDto implements AbstractRequest {

    @NotBlank
    @ApiModelProperty(required = true)
    private String companyName;

    private List<String> tags = new ArrayList<>();

    private List<ResourceFileDto> files = new ArrayList<>();

    @NotNull
    private City city;

    @NotBlank
    private String description;

    private String benefits;
}
