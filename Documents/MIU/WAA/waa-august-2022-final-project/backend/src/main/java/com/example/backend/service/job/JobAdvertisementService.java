package com.example.backend.service.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.domain.job.Tag;
import com.example.backend.domain.user.LocalUser;
import com.example.backend.domain.user.Student;
import com.example.backend.dto.filter.JobAdvertisementSearch;
import com.example.backend.dto.job.JobAdvertisementCreateRequestDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.dto.messaging.Note;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.JobAdvertisementMapper;
import com.example.backend.repo.job.JobAdvertisementRepo;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.messaging.FirebaseMessagingService;
import com.example.backend.service.security.Security;
import com.example.backend.utils.DaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class JobAdvertisementService implements JobAdvertisements {

    private final JobAdvertisementRepo repo;
    private final JobAdvertisementMapper mapper;
    private final TagRepo tagRepo;
    private final Security security;

    private final FirebaseMessagingService messagingService;
    private final StudentRepo studentRepo;

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

        var tags = tagRepo.findAllByTitleIsIn(dto.getTags());

        advertisement.setTags(tags);
        advertisement.setCreator(security.getCurrentUser());

        var result = repo.save(advertisement);

        sendNotification(result);

        return mapper.toDto(result);
    }

    @Override
    public JobAdvertisementDto update(Long id, JobAdvertisementCreateRequestDto dto) {
        LocalUser localUser = security.getCurrentUser();
        Optional<JobAdvertisement> advertisement = repo.findById(id);
        if (advertisement.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND);
        }

        JobAdvertisement jobAdvertisement = advertisement.get();

        log.info(localUser.getId().toString());
        log.info(jobAdvertisement.getCreator().getId().toString());

        if (!localUser.getId().equals(jobAdvertisement.getCreator().getId())) {
            throw new AccessDeniedException(ErrorCode.FORBIDDEN + " Users can only update their own advertisements");
        }

        tagRepo.saveAll(dto.getTags().stream().filter(item -> tagRepo.findByTitle(item).isEmpty()).map(Tag::new).collect(Collectors.toList()));
        var tags = tagRepo.findAllByTitleIsIn(dto.getTags());

        jobAdvertisement.setCompanyName(dto.getCompanyName());
        jobAdvertisement.setTags(tags);
        jobAdvertisement.setCity(dto.getCity());
        jobAdvertisement.setDescription(dto.getDescription());
        jobAdvertisement.setBenefits(dto.getBenefits());

        return mapper.toDto(repo.save(jobAdvertisement));
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
        }, DaoUtils.toPaging(search)).map(mapper::toDto);
    }

    @Override
    public List<JobAdvertisementDto> getRecentlyAppliedAds() {
        return repo.findRecentlyAppliedAds().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<JobAdvertisement> jobAdvertisementOptional = repo.findById(id);
        if (jobAdvertisementOptional.isPresent()) {
            JobAdvertisement advertisement = jobAdvertisementOptional.get();
            LocalUser localUser = security.getCurrentUser();
            if (localUser.getId().equals(advertisement.getCreator().getId())) {
                repo.deleteById(id);
            } else {
                throw new AccessDeniedException(ErrorCode.FORBIDDEN + " Users can only delete their own advertisements");
            }
        }
    }

    @SneakyThrows
    private void sendNotification(JobAdvertisement advertisement) {
        var data = new HashMap<String, String>();
        data.put("advertisement", new ObjectMapper().writeValueAsString(mapper.toDto(advertisement)));

        var note = Note.builder()
                .subject("A new job advertisement has been added")
                .content("Hey do not waste your change to secure your job!")
                .data(data)
                .build();

        messagingService.sendNotifications(note,
                getStudentsByTags(advertisement.getTags()).stream().map(LocalUser::getFirebaseToken).collect(Collectors.toList()));
    }

    private List<Student> getStudentsByTags(List<Tag> tags) {
        return studentRepo.findAllByTags(tags);
    }
}
