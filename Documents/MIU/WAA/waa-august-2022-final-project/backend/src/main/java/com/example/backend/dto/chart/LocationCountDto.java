package com.example.backend.dto.chart;

import com.example.backend.domain.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationCountDto {

    private State state;
    private String city;
    private Long count;

    private String name;

    public LocationCountDto(State state, String city, Long count) {
        this.state = state;
        this.city = city;
        this.count = count;
    }


}
