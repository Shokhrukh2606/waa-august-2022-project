package com.example.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentOnStudentCreateRequest {

    @NotNull
    private Long studentId;
    @NotBlank
    private String comment;
}
