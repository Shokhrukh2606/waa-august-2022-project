package com.example.backend.domain.user;

import com.example.backend.domain.file.ResourceFile;
import com.example.backend.domain.job.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
@Where(clause = "deleted=false")
@ToString
public class Student extends LocalUser {

    @Column(columnDefinition = "NUMERIC(5,2)")
    private Float gpa;

    @Enumerated(value = EnumType.STRING)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "cv_url")
    private String cvUrl;

    public Student(String email, String firstname, String lastname) {
        super(email, firstname, lastname);
    }
}
