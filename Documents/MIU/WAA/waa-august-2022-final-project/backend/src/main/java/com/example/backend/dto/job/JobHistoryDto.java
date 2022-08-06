package com.example.backend.dto.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobHistoryDto {

    private Long id;

    private List<TagDto> tags = new ArrayList<>();

    private String companyName;

    private String startDate;

    private String endDate;

    private String reasonToLeave;

    private String comments;
}
