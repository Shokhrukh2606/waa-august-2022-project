package com.example.backend.repo.job;

import com.example.backend.domain.job.Tag;
import com.example.backend.dto.chart.LocationCountEntity;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepo extends SoftDeleteJpaRepository<Tag> {

    Optional<Tag> findByTitle(String title);

    List<Tag> findAllByTitleIsIn(List<String> titles);

    @Query(value = " select c.state as state, c.name as city, count(t.id) as count, "
                   + "       t.title as name from cities c "
                   + "left join job_advertisements ja on c.id = ja.city_id "
                   + "left join job_tags jt on ja.id = jt.job_id "
                   + "inner join tags t on jt.tag_id = t.id "
                   + "group by c.state, c.name, t.title ", nativeQuery = true)
    List<LocationCountEntity> findAllTagsByLocation();
}
