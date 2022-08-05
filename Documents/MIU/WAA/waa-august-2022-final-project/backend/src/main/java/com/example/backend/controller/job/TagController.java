package com.example.backend.controller.job;

import com.example.backend.dto.filter.TagSearch;
import com.example.backend.dto.job.TagDto;
import com.example.backend.service.job.Tags;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/tags")
public class TagController {

    private final Tags tags;

    @GetMapping("/filter")
    public List<TagDto> searchTags(@Valid TagSearch search){
        return tags.search(search);
    }

}
