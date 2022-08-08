package com.example.backend.controller.chart;

import com.example.backend.domain.State;
import com.example.backend.dto.chart.LocationCountDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.chart.Charts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/charts")
@ApiModel("Endpoint to provide chart data")
public class ChartController {

    private final Charts charts;

    @GetMapping("/job-advertisement-count-by-location")
    @ApiOperation(value = "Getting the list of Job advertisements per location", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getJobAdvertisementsPerLocation() {
        return charts.getJobAdvertisementsPerLocation();
    }

    @GetMapping("/students-by-all-states")
    @ApiOperation(value = "Getting the list of students per state", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getStudentsByAllStates() {
        return charts.getStudentsByAllStates();
    }

    @GetMapping("/states/{name}/students-by-all-cities")
    @ApiOperation(value = "Getting the list of students per city", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getStudentsByAllCities(@PathVariable("name") State state) {
        return charts.getStudentsByAllCities(state);
    }

    @GetMapping("/tags-count-by-location")
    @ApiOperation(value = "Getting the list of tags per location", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getAllTagsByLocation() {
        return charts.getAllTagsByLocation();
    }


}
