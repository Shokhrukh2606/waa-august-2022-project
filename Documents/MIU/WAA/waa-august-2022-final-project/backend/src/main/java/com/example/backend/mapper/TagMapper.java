package com.example.backend.mapper;

import com.example.backend.domain.job.Tag;
import com.example.backend.dto.job.TagDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper extends AbstractMapper {


    Tag fromDto(TagDto dto);

    TagDto toDto(Tag tag);
}
