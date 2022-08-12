package com.example.backend.controller.user;

import com.example.backend.dto.user.LocalUserCreateRequest;
import com.example.backend.dto.user.LocalUserDto;
import com.example.backend.service.security.Security;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/users")
@ApiModel("Endpoint for handling new users")
@Slf4j
public class UserController {

    private final Security security;

    @PostMapping
    public LocalUserDto createAuthenticatedUser(@Valid @RequestBody LocalUserCreateRequest request) {
        return security.createAuthenticatedUser(request);
    }
}
