package com.example.backend.service.user;

import com.example.backend.domain.job.Tag;
import com.example.backend.domain.user.Student;
import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.dto.user.StudentUpdateRequest;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.security.Security;
import com.example.backend.utils.DaoUtils;
import com.example.backend.utils.KeyCloakUtils;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService implements Students {

    private final StudentRepo repo;
    private final StudentMapper mapper;

    private final TagRepo tagRepo;
    private final Security security;

    private final KeyCloakUtils keyCloakUtils;

    @Override
    public StudentDto updateProfile(StudentUpdateRequest request) {

        tagRepo.saveAll(request.getTags().stream().filter(item -> tagRepo.findByTitle(item).isEmpty()).map(Tag::new).collect(Collectors.toList()));
        var tags = tagRepo.findAllByTitleIsIn(request.getTags());

        Student student = repo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new);

        student.setCity(request.getCity());
        student.setGpa(request.getGpa());
        student.setMajor(request.getMajor());
        student.setCvUrl(request.getCvUrl());

        if (!ObjectUtils.isEmpty(request.getFirstname())) {
            student.setFirstname(request.getFirstname());
        }

        if (!ObjectUtils.isEmpty(request.getLastname())) {
            student.setLastname(request.getLastname());
        }

        if (!ObjectUtils.isEmpty(request.getEmail())) {
            student.setEmail(request.getEmail());
        }

        student.setTags(tags);
        student.setUpdated(true);

        var userResource = keyCloakUtils.getUserResource(student.getKeyClockUserId());
        var userRepresentation = keyCloakUtils.getUserRepresentation(userResource);

        if (!ObjectUtils.isEmpty(request.getFirstname())) {
            userRepresentation.setFirstName(request.getFirstname());
        }

        if (!ObjectUtils.isEmpty(request.getLastname())) {
            userRepresentation.setLastName(request.getLastname());
        }

        if (!ObjectUtils.isEmpty(request.getEmail())) {
            userRepresentation.setEmail(request.getEmail());
        }

        userResource.update(userRepresentation);

        return mapper.toDto(repo.save(student));
    }

    @Override
    public StudentDto getAuthorizedStudent() {
        return mapper.toDtoWithCV(repo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<StudentDto> search(StudentSearch search) {
        return repo.findAll(specificationForSearch(search), DaoUtils.toPaging(search)).map(mapper::toDto);
    }

    private Specification<Student> specificationForSearch(StudentSearch search){
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(search.getId())) {
                predicates.add(cb.equal(root.get("id"), search.getId()));
            }
            if (!ObjectUtils.isEmpty(search.getCity())) {
                predicates.add(cb.equal(root.get("city").get("name"), search.getCity()));
            }

            if (!ObjectUtils.isEmpty(search.getTags())) {
                var join = root.join("tags").get("title");
                List<Predicate> pres = new ArrayList<>();
                search.getTags().forEach(item -> pres.add(cb.equal(join, item)));
                Predicate or = cb.or(pres.toArray(Predicate[]::new));
                predicates.add(cb.and(or));
            }

            if (!ObjectUtils.isEmpty(search.getState())) {
                predicates.add(cb.equal(root.get("city").get("state"), search.getState().name()));
            }
            if (!ObjectUtils.isEmpty(search.getName())) {
                Predicate firstname = cb.like(root.get("firstname"), DaoUtils.toLikeCriteria(search.getName()));
                Predicate lastname = cb.like(root.get("lastname"), DaoUtils.toLikeCriteria(search.getName()));

                Predicate orPredicates = cb.or(firstname, lastname);
                predicates.add(cb.and(orPredicates));
            }
            if (!ObjectUtils.isEmpty(search.getMajor())) {
                predicates.add(cb.equal(root.get("major"), search.getMajor().name()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public List<Student> findAll(StudentSearch search) {
        return repo.findAll(specificationForSearch(search));
    }
}
