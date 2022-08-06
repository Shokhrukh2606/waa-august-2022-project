package com.example.backend.dto.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.example.backend.utils.TimeUtils.DATE_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobHistoryCreateRequest {

    private List<String> tags = new ArrayList<>();

    private String companyName;
    @ApiModelProperty(example = DATE_FORMAT)
    private String startDate;

    private String endDate;

    private String reasonToLeave;

    private String comments;
}
