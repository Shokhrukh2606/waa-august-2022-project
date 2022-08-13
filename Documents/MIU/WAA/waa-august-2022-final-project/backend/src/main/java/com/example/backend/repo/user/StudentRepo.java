package com.example.backend.repo.user;

import com.example.backend.domain.job.Tag;
import com.example.backend.domain.user.Student;
import com.example.backend.dto.chart.LocationCountEntity;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends SoftDeleteJpaRepository<Student> {

    Optional<Student> findByEmail(String email);

    @Query(value = "select c.state as state, count(s.id) as count "
                   + "from cities c "
                   + "         left join students s on c.id = s.city_id "
                   + "group by c.state order by count desc", nativeQuery = true)
    List<LocationCountEntity> findStudentsByAllStates();

    @Query(value = "select c.name as city, count(s.id) as count "
                   + "from cities c "
                   + "         left join students s on c.id = s.city_id "
                   + "where c.state = :state group by c.name order by count desc", nativeQuery = true)
    List<LocationCountEntity> findStudentsByAllCities(String state);

    @Query(value = "select s from students s join tags t where t in (:tags) ")
    List<Student> findAllByTags(List<Tag> tags);
}
