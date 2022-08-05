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

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "job_history")
public class JobHistory extends AbstractEntity {

    @ManyToMany
    @JoinTable(name = "job_history_tags", joinColumns = {@JoinColumn(name = "job_history_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)})
    private List<Tag> tags;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "reason_to_leave", columnDefinition = "TEXT")
    private String reasonToLeave;

    @Column(columnDefinition = "TEXT")
    private String comments;



}
