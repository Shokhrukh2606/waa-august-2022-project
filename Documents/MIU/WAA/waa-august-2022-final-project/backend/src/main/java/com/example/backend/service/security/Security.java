package com.example.backend.service.security;

import com.example.backend.domain.user.LocalUser;

public interface Security {

    LocalUser getCurrentUser();
}
