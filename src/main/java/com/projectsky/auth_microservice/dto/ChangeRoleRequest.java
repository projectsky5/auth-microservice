package com.projectsky.auth_microservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeRoleRequest(
        @NotBlank
        String role
) {
}
