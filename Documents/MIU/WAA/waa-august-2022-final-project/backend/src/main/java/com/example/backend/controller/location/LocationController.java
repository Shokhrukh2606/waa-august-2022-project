package com.example.backend.controller.location;

import com.example.backend.domain.State;
import com.example.backend.domain.job.City;
import com.example.backend.dto.filter.LocationSearch;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.location.Locations;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/locations")
@ApiModel("Endpoint for handling locations")
public class LocationController {

    private final Locations locations;

    @GetMapping("/states")
    @ApiOperation(value = "Getting the list of states", response = State.class, responseContainer = "List")
    private List<State> getStates() {
        return locations.getStates();
    }

    @GetMapping("/cities/filter")
    @ApiOperation(value = "Getting the list of cities", response = City.class, responseContainer = "List")
    private List<City> searchCities(@Valid LocationSearch search) {
        return locations.search(search);
    }
}
