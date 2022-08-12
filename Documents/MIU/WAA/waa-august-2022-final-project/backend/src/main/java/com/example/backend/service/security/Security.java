package com.example.backend.service.security;

import com.example.backend.domain.user.LocalUser;
import com.example.backend.dto.user.LocalUserCreateRequest;
import com.example.backend.dto.user.LocalUserDto;

public interface Security {

    LocalUser getCurrentUser();

    LocalUserDto createAuthenticatedUser(LocalUserCreateRequest request);
}
