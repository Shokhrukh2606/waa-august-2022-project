package com.example.backend.dto.user;

import com.example.backend.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LocalUserCreateRequest {

    @NotNull
    private Role role;

    private String firebaseToken;
}
