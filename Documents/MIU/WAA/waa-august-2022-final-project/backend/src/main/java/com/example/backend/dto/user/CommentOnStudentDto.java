package com.example.backend.dto.user;

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
public class CommentOnStudentDto {

    private Long id;
    private FacultyDto facultyMember;
    private StudentDto student;
    private String comment;

}
