package com.projectsky.auth_microservice.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String role
) {
}
