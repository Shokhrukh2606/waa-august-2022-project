package com.example.backend.controller.location;

import com.example.backend.domain.State;
import com.example.backend.domain.job.City;
import com.example.backend.dto.filter.LocationSearch;
import com.example.backend.service.location.Locations;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/locations")
public class LocationController {

    private final Locations locations;

    @GetMapping("/states")
    private List<State> getStates() {
        return locations.getStates();
    }

    @GetMapping("/cities/filter")
    private List<City> searchCities(@Valid LocationSearch search) {
        return locations.search(search);
    }
}
