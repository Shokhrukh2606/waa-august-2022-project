package com.example.backend.service.job;

import com.example.backend.domain.job.Tag;
import com.example.backend.dto.filter.TagSearch;
import com.example.backend.dto.job.TagDto;
import com.example.backend.mapper.TagMapper;
import com.example.backend.repo.job.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TagBean implements Tags {

    private final TagRepo tagRepo;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> search(TagSearch search) {
        return tagRepo.findAll((Specification<Tag>) (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(search.getTitle())){
                predicates.add(cb.like(root.get("title"), search.getTitle()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }).stream().map(tagMapper::toDto).collect(Collectors.toList());
    }
}
