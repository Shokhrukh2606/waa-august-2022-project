package com.example.backend.controller.chart;

import com.example.backend.domain.State;
import com.example.backend.dto.chart.LocationCountDto;
import com.example.backend.service.chart.Charts;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/charts")
public class ChartController {

    private final Charts charts;

    @GetMapping("/job-advertisement-count-by-location")
    public List<LocationCountDto> getJobAdvertisementsPerLocation() {
        return charts.getJobAdvertisementsPerLocation();
    }

    @GetMapping("/students-by-all-states")
    public List<LocationCountDto> getStudentsByAllStates() {
        return charts.getStudentsByAllStates();
    }

    @GetMapping("/states/{name}/students-by-all-cities")
    public List<LocationCountDto> getStudentsByAllCities(@PathVariable("name") State state) {
        return charts.getStudentsByAllCities(state);
    }

    @GetMapping("/tags-count-by-location")
    public List<LocationCountDto> getAllTagsByLocation() {
        return charts.getAllTagsByLocation();
    }


}
