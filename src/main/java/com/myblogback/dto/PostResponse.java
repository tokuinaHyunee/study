package com.myblogback.dto;

import com.myblogback.domain.entity.Post;
import java.time.LocalDateTime;

public record PostResponse (
        Long id,
        String title,
        String content,
        String authorNickname,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        // String thumbnailUrl <-이건 썸네일 넣을거면
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getNickname(),
                post.getCreatedAt(),
                post.getUpdatedAt()
                // post.getThumbnailUrl() <-이건 썸네일 넣을거면
        );
    }
}
