package com.example.backend.service.job;

import com.example.backend.domain.user.Student;
import com.example.backend.dto.filter.StudentSearch;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.utils.DaoUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class StudentService implements Students {

    private final StudentRepo repo;
    private final StudentMapper mapper;


    //todo only faculty member can filter students
    @Override
    public Page<StudentDto> search(StudentSearch search) {
        return repo.findAll((Specification<Student>) (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(search.getId())) {
                predicates.add(cb.equal(root.get("id"), search.getId()));
            }
            if (!ObjectUtils.isEmpty(search.getCity())) {
                predicates.add(cb.equal(root.get("city").get("name"), search.getCity()));
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
        }, DaoUtils.toPaging(search)).map(mapper::toDto);
    }
}
