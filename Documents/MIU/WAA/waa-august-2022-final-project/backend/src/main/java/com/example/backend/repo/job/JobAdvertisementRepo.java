package com.example.backend.repo.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.dto.chart.LocationCountEntity;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobAdvertisementRepo extends SoftDeleteJpaRepository<JobAdvertisement> {

    @Query(value = "select c.state as state, c.name as city, count(ja.id) as count from job_advertisements ja "
                   + "inner join cities c on c.id = ja.city_id "
                   + "group by c.state, c.name", nativeQuery = true)
    List<LocationCountEntity> findJobAdvertisementByLocation();

    @Query(value = "select ja.* from job_advertisements ja "
                   + "inner join job_applicants j on ja.id = j.job_advertisement_id "
                   + "order by j.create_date desc limit 10", nativeQuery = true)
    List<JobAdvertisement> findRecentlyAppliedAds();
}
