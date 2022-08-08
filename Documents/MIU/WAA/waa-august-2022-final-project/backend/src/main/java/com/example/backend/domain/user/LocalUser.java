package com.example.backend.domain.user;

import com.example.backend.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class LocalUser extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String email;

    private String firstname;

    private String lastname;

    private String keyClockUserId;

}
