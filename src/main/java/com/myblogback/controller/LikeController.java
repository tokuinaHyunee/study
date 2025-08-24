package com.myblogback.controller;

import com.myblogback.common.session.SessionConst;
import com.myblogback.dto.LikeResponse;
import com.myblogback.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    //게시글 좋아요
    //게시글 좋아요 상태/개수 조회(로그인여부무관)
    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<?> getPostLikes(@PathVariable Long postId, HttpSession session) {
        Long userId = currentUserId(session);
        LikeResponse resp = likeService.getPostLike(postId, userId);
        return ResponseEntity.ok(resp);
    }

    //게시글 좋아요(로그인 필요)
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<?> togglePostLike(@PathVariable Long postId, HttpSession session) {
        Long userId = currentUserId(session);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        return likeService.togglePostLike(postId, userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "처리 실패")));
    }

    //댓글 좋아요
    //댓글 좋아요상태/개수 조회(로그인여부무관)
    @GetMapping("/comments/{commentId}/likes")
    public ResponseEntity<?> getCommentLikes(@PathVariable Long commentId, HttpSession session) {
        Long userId = currentUserId(session);
        LikeResponse resp = likeService.getCommentLike(commentId, userId);
        return ResponseEntity.ok(resp);
    }

    //댓글 좋아요(로그인필요)
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<?> toggleCommentLike(@PathVariable Long commentId, HttpSession session) {
        Long userId = currentUserId(session);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        return likeService.toggleCommentLike(commentId, userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "처리 실패")));
    }

    // 세션에서 현재 사용자 ID를 Long 타입으로 안전하게 가져오기
    private Long currentUserId(HttpSession session) {
        Object v = session.getAttribute(SessionConst.LOGIN_USER_ID);
        if (v instanceof Long l) return l;
        if (v instanceof Integer i) return i.longValue();
        if (v instanceof String s) { try { return Long.parseLong(s); } catch (NumberFormatException ignored) {} }
        return null;
    }
}
