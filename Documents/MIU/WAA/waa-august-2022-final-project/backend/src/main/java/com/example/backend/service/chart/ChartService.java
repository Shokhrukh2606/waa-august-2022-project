package com.example.backend.service.chart;

import com.example.backend.domain.State;
import com.example.backend.dto.chart.LocationCountDto;
import com.example.backend.repo.job.JobAdvertisementRepo;
import com.example.backend.repo.job.TagRepo;
import com.example.backend.repo.user.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChartService implements Charts {

    private final JobAdvertisementRepo advertisementRepo;
    private final StudentRepo studentRepo;

    private final TagRepo tagRepo;

    @Override
    public List<LocationCountDto> getJobAdvertisementsPerLocation() {
        return advertisementRepo.findJobAdvertisementByLocation().stream().map(t ->
                        new LocationCountDto(State.getByTitle(t.getState()), t.getCity(), t.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationCountDto> getStudentsByAllStates() {
        return studentRepo.findStudentsByAllStates().stream().map(t ->
                        new LocationCountDto(State.getByTitle(t.getState()), null, t.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationCountDto> getStudentsByAllCities(State state) {
        return studentRepo.findStudentsByAllCities(state.getTitle()).stream().map(t ->
                        new LocationCountDto(null, t.getCity(), t.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationCountDto> getAllTagsByLocation() {
        return tagRepo.findAllTagsByLocation().stream().map(t ->
                        LocationCountDto.builder()
                                .state(State.getByTitle(t.getState()))
                                .city(t.getCity())
                                .count(t.getCount())
                                .name(t.getName())
                                .build())
                .collect(Collectors.toList());
    }
}
