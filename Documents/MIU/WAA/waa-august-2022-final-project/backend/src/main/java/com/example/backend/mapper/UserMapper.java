package com.example.backend.mapper;

import com.example.backend.domain.user.LocalUser;
import com.example.backend.dto.user.LocalUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    LocalUserDto toDto(LocalUser user);
}
