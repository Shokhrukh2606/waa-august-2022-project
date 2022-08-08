package com.example.backend.service.chart;

import com.example.backend.domain.State;
import com.example.backend.dto.chart.LocationCountDto;

import java.util.List;

public interface Charts {

    List<LocationCountDto> getJobAdvertisementsPerLocation();

    List<LocationCountDto> getStudentsByAllStates();

    List<LocationCountDto> getStudentsByAllCities(State state);

    List<LocationCountDto> getAllTagsByLocation();

}
