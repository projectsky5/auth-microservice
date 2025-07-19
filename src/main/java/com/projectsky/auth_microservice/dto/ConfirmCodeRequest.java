package com.projectsky.auth_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConfirmCodeRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6, max = 6, message = "Code must be exactly 6 characters")
        @Pattern(regexp = "\\d{6}", message = "Code must contain exactly 6 digits")
        String code
) {
}
