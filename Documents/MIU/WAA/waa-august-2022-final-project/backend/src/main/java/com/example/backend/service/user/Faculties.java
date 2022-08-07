package com.example.backend.service.user;

import com.example.backend.dto.user.FacultyDto;
import com.example.backend.dto.user.FacultyUpdateRequest;

public interface Faculties {

    FacultyDto updateProfile(FacultyUpdateRequest request);

}
