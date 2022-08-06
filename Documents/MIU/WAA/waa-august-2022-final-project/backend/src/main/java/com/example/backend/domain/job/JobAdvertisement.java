package com.example.backend.domain.job;

import com.example.backend.domain.AbstractEntity;
import com.example.backend.domain.file.ResourceFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

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
@Where(clause = "deleted=false")
@Entity(name = "job_advertisements")
public class JobAdvertisement extends AbstractEntity {

    @Column(name = "company_name")
    private String companyName;

    @ManyToMany
    @JoinTable(name = "job_tags", joinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)})
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(name = "job_files", joinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)})
    private List<ResourceFile> files;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(columnDefinition = "VARCHAR(2048)")
    private String description;

    @Column(columnDefinition = "VARCHAR(2048)")
    private String benefits;


}
