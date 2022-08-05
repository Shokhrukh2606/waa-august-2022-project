package com.example.backend.domain.job;

import com.example.backend.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "job_advertisements")
public class JobAdvertisement extends AbstractEntity {

    @ManyToMany
    @JoinTable(name = "job_tags", joinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)})
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(columnDefinition = "VARCHAR(2048)")
    private String description;

    @Column(columnDefinition = "VARCHAR(2048)")
    private String benefits;


}
