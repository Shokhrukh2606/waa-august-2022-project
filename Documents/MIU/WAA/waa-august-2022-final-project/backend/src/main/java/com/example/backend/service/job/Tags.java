package com.example.backend.service.job;

import com.example.backend.dto.filter.TagSearch;
import com.example.backend.dto.job.TagDto;

import java.util.List;

public interface Tags {

    List<TagDto> search(TagSearch search);
}
