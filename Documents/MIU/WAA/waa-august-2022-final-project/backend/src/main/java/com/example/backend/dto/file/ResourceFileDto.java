package com.example.backend.dto.file;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("Resource File Dto ")
public class ResourceFileDto {

    private Long id;
    private String name;
    private String url;
}
