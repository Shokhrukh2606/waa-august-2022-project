package com.example.backend.dto.user;

import com.example.backend.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LocalUserDto {

    private String email;

    private String firstname;

    private String lastname;

    private Role role;

}
