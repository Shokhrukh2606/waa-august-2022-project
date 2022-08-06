package com.example.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "faculties")
public class Faculty extends LocalUser {

    @Enumerated(value = EnumType.STRING)
    private Department department;

    public Faculty(String email, String firstname, String lastname) {
        super(email, firstname, lastname);
    }
}
