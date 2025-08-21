package com.myblogback.domain.entity;

import com.myblogback.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    @Table(name = "posts", indexes = {
            @Index(name = "idx_posts_created_at", columnList = "createdAt")
    })
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동 증가 시켜줌
        private Long id;

        @Column(nullable = false, length = 200)
        private String title;

        @Lob
        @Column(nullable = false)
        private String content;
        //필요하면 썸네일 url 추가 (private String thumbnailUrl;)

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        private User author;

        @Column(nullable = false)
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        @PrePersist
        void onCreate() {
            if (createdAt == null) createdAt = LocalDateTime.now();
        }
        @PreUpdate
        void onUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }
