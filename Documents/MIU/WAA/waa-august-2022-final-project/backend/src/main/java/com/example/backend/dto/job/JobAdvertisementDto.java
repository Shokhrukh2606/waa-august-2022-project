package com.example.backend.dto.job;

import com.example.backend.domain.job.City;
import com.example.backend.dto.file.ResourceFileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobAdvertisementDto {

    private Long id;

    private String companyName;

    private List<TagDto> tags;

    private List<ResourceFileDto> files;

    private City city;

    private String description;

    private String benefits;
}
