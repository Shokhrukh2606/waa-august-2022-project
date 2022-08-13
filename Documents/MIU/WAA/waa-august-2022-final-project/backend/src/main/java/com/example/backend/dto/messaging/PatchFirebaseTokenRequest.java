package com.example.backend.dto.messaging;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class PatchFirebaseTokenRequest {

    @NotBlank
    private String token;
}
