package com.example.backend.service.location;

import com.example.backend.domain.State;
import com.example.backend.domain.job.City;
import com.example.backend.dto.filter.LocationSearch;

import java.util.List;

public interface Locations  {

    List<State> getStates();

    List<City> search(LocationSearch search);

}
