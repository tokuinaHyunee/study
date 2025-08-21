package com.myblog.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email", unique = true)
}) //이거 무슨기능인지 알아보기

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String passwordHash;

    @Column(nullable = false, length = 40)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @PrePersist
    void onCreate() {
        if (createAt == null) createAt = LocalDateTime.now(); //가입날짜 null값이면 insert 직전에 자동으로 세팅
    }
}
