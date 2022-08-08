package com.example.backend.controller.job;

import com.example.backend.dto.filter.TagSearch;
import com.example.backend.dto.job.TagDto;
import com.example.backend.service.job.Tags;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/tags")
@ApiModel("Endpoint for handling tags for job advertisement")
@Slf4j
public class TagController {

    private final Tags tags;

    @GetMapping("/filter")
    @ApiOperation(value="Getting the list of tags", response=TagDto.class, responseContainer = "List")
    public List<TagDto> searchTags(@ApiParam("Request list") @Valid TagSearch search){

        log.info("Accessing GET api/tags/filter {}", search);

        var result = tags.search(search);

        log.info("{} tag list was retrieved", result);

        return result;
    }

}
