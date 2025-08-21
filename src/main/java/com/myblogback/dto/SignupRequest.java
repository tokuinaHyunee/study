package com.myblog.dto;

import jakarta.validation.constraints.*;

public record SignupRequest (
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, max = 64)
        @Pattern(regexp=".*[!@#\\$%\\^&\\*].*", message="특수문자 하나 이상 포함하세요.")
        String password,

        @NotBlank
        @Size(min = 2, max = 20)
        String nickname
) {}
