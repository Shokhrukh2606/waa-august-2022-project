package com.example.backend.repo.location;

import com.example.backend.domain.job.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

}
