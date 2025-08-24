package com.myblogback.repository;

import com.myblogback.domain.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
    long countByCommentId(Long commentId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
