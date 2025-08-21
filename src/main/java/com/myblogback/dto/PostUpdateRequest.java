package com.myblogback.dto;

import jakarta.validation.constraints.*;

public record PostUpdateRequest (
        @NotBlank
        @Size(max = 200)
        String title,

        @NotBlank
        String content
        // String thumbnailUrl <-이건 썸네일 넣을거면
) {}
