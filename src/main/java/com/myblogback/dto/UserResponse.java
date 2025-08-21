package com.myblog.dto;

import com.myblog.domain.entity.User;
import java.time.LocalDateTime;

public record UserResponse (
        Long id,
        String email,
        String nickname,
        LocalDateTime createAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getCreateAt());
    }
} // 사용자 응답기능
