package com.myblogback.repository;


import com.myblogback.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {} // 게시글 저장/조회용 JPA 레포지토리
