package com.example.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
public class Student extends LocalUser {

    @Column(columnDefinition = "NUMERIC(5,2)")
    private Float gpa;

    @Enumerated(value = EnumType.STRING)
    private Major major;

}
