package com.example.backend.dto.job;

import com.example.backend.dto.user.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobApplicantDto {

    private JobAdvertisementDto jobAdvertisement;
    private StudentDto student;
}
