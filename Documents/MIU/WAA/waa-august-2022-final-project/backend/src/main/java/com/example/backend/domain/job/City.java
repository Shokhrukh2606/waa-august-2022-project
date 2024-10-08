package com.example.backend.domain.job;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "cities")
public class City {

    @Id
    private Integer id;

    private String name;

    private String state;
}
