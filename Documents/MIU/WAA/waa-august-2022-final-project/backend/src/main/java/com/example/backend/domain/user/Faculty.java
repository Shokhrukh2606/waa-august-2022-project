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

    public static Faculty createFromParent(LocalUser user){
        var faculty = new Faculty();
        faculty.setEmail(user.getEmail());
        faculty.setFirstname(user.getFirstname());
        faculty.setLastname(user.getLastname());
        faculty.setKeyClockUserId(user.getKeyClockUserId());
        faculty.setRole(user.getRole());
        faculty.setFirebaseToken(user.getFirebaseToken());

        return faculty;
    }
}
