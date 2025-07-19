package com.projectsky.auth_microservice.dto;

public record JwtAunthenticationDto(
        String token,
        String refreshToken
) {
}
