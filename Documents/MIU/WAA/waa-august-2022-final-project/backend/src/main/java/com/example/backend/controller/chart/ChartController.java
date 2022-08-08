package com.example.backend.controller.chart;

import com.example.backend.domain.State;
import com.example.backend.dto.chart.LocationCountDto;
import com.example.backend.dto.job.JobAdvertisementDto;
import com.example.backend.service.chart.Charts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/charts")
@ApiModel("Endpoint to provide chart data")
@Slf4j
public class ChartController {

    private final Charts charts;

    @GetMapping("/job-advertisement-count-by-location")
    @ApiOperation(value = "Getting the list of Job advertisements per location", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getJobAdvertisementsPerLocation() {

        log.info("Accessing GET api/charts/job-advertisement-count-by-location");

        var result = charts.getJobAdvertisementsPerLocation();

        log.info("{} list of job advertisement count by location", result);

        return result;
    }

    @GetMapping("/students-by-all-states")
    @ApiOperation(value = "Getting the list of students by state", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getStudentsByAllStates() {

        log.info("Accessing GET api/charts/students-by-all-states");

        var result = charts.getStudentsByAllStates();

        log.info("{} list of students by all states ", result);

        return result;
    }

    @GetMapping("/states/{name}/students-by-all-cities")
    @ApiOperation(value = "Getting the list of students by city", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getStudentsByAllCities(@PathVariable("name") State state) {

        log.info("Accessing GET api/charts/states/{}/students-by-all-cities", state);

        var result = charts.getStudentsByAllCities(state);

        log.info("{} list of students by all cities ", result);

        return result;
    }

    @GetMapping("/tags-count-by-location")
    @ApiOperation(value = "Getting the list of tags by location", response = LocationCountDto.class, responseContainer = "List")
    public List<LocationCountDto> getAllTagsByLocation() {

        log.info("Accessing GET api/charts/tags-count-by-location");

        var result = charts.getAllTagsByLocation();

        log.info("{} list of tags by locations ", result);

        return result;
    }


}
