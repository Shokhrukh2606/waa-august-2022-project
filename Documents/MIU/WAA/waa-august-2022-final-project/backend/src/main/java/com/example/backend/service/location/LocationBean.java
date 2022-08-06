package com.example.backend.service.location;

import com.example.backend.domain.State;
import com.example.backend.domain.job.City;
import com.example.backend.dto.filter.LocationSearch;
import com.example.backend.repo.location.CityRepo;
import com.example.backend.utils.DaoUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class LocationBean implements Locations {

    private final CityRepo repo;

    @Override
    public List<State> getStates() {
        return Arrays.stream(State.values()).toList();
    }

    @Override
    public List<City> search(LocationSearch search) {
        return repo.findAll((Specification<City>) (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(search.getCity())) {
                predicates.add(cb.like(root.get("name"), DaoUtils.toLikeCriteria(search.getCity())));
            }

            predicates.add(cb.equal(root.get("state"), search.getState().name()));

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
