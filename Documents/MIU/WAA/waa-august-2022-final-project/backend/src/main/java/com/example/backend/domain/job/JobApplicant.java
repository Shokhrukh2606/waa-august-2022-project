package com.example.backend.domain.job;

import com.example.backend.domain.AbstractEntity;
import com.example.backend.domain.user.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "job_applicants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class JobApplicant extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "job_advertisement_id")
    private JobAdvertisement jobAdvertisement;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
