package com.projectsky.auth_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterInitRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 12, message = "Login must be between 6 and 12 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login can contain only letters and numbers")
        String login
) {
}
