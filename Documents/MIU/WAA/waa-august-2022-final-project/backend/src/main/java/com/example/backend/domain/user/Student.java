package com.example.backend.domain.user;

import com.example.backend.domain.job.City;
import com.example.backend.domain.job.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
@Where(clause = "deleted=false")
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

    @ManyToMany
    @JoinTable(name = "student_tags", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)})
    private List<Tag> tags;

    public Student(String email, String firstname, String lastname) {
        super(email, firstname, lastname);
    }
}
