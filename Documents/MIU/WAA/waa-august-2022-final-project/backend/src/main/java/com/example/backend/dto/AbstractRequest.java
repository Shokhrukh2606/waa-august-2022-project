package com.example.backend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public interface AbstractRequest {

    @SneakyThrows
    default String toJsonString() {
        return new ObjectMapper().writeValueAsString(this);
    }

}
