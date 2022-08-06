package com.example.backend.service.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.domain.job.Tag;
import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.FileMapper;
import com.example.backend.mapper.JobAdvertisementMapper;
import com.example.backend.repo.file.FileRepo;
import com.example.backend.repo.file.JobAdvertisementRepo;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.service.security.Security;
import com.example.backend.utils.DaoUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JobAdvertisementService implements JobAdvertisements {

    private final JobAdvertisementRepo repo;
    private final JobAdvertisementMapper mapper;
    private final TagRepo tagRepo;
    private final Security security;

    @Override
    public JobAdvertisementDto create(JobAdvertisementCreateRequestDto dto) {
        JobAdvertisement advertisement = mapper.fromCreate(dto);

        if (ObjectUtils.isEmpty(dto.getCompanyName())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "companyName");
        }

        if (ObjectUtils.isEmpty(dto.getCity())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "city");
        }

        if (ObjectUtils.isEmpty(dto.getDescription())) {
            throw new LocalizedApplicationException(ErrorCode.MISSING_REQUIRED_FIELD, "description");
        }

        tagRepo.saveAll(dto.getTags().stream().filter(item -> tagRepo.findByTitle(item).isEmpty()).map(Tag::new).collect(Collectors.toList()));

        var tags = tagRepo.findAllByTitleIsIn(dto.getTags());//todo check it

        advertisement.setTags(tags);
        advertisement.setCreator(security.getCurrentUser());

        return mapper.toDto(repo.save(advertisement));
    }

    @Override
    public Page<JobAdvertisementDto> search(JobAdvertisementSearch search) {
        return repo.findAll((Specification<JobAdvertisement>) (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(search.getTags())) {
                var join = root.join("tags").get("title");
                search.getTags().forEach(item -> predicates.add(cb.equal(join, item)));
            }

            if (!ObjectUtils.isEmpty(search.getState())) {
                predicates.add(cb.equal(root.get("city").get("state"), search.getState().name()));
            }
            if (!ObjectUtils.isEmpty(search.getCity())) {
                predicates.add(cb.equal(root.get("city").get("name"), search.getCity()));
            }
            if (!ObjectUtils.isEmpty(search.getCompanyName())) {
                predicates.add(cb.like(root.get("companyName"), DaoUtils.toLikeCriteria(search.getCompanyName())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search)).map(mapper::toSimpleDto);
    }
}
