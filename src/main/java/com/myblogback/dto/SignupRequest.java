package com.myblogback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 64)
        @Pattern(regexp = "^(?=.*[!@#$%^&*]).+$")
        String password,

        @NotBlank
        @Size(min = 2, max = 20)
        String nickname
) {}