package com.myblogback.controller;

import com.myblogback.common.session.SessionConst;
import com.myblogback.dto.*;
import com.myblogback.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PostCreateRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute(SessionConst.USER_ID);
        if (userId == null) return ResponseEntity.status(401).body(Map.of("message", "로그인 필요"));
        try {
            return ResponseEntity.status(201).body(postService.create(userId, request));
    } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
        }
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<Page<PostResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
      PostResponse response = postService.get(id);
      if (response == null) return ResponseEntity.status(404).body(Map.of("message", "게시글 못찾겠음"));
      return ResponseEntity.ok(response);
    } // 게시글 조회

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request, HttpSession session) {
    Long userId = (Long) session.getAttribute(SessionConst.USER_ID);
    if (userId == null) return ResponseEntity.status(401).body(Map.of("message", "로그인 필요"));
    try {
        return ResponseEntity.ok(postService.update(userId, id, request));
    } catch (IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
    }
    } // 게시글 수정 작성자만 가능

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession session) {
     Long userId = (Long) session.getAttribute((SessionConst.USER_ID));
     if (userId == null) return ResponseEntity.status(401).body(Map.of("message", "로그인 필요"));
     try {
         postService.delete(userId, id);
         return ResponseEntity.noContent().build();
     } catch (IllegalArgumentException exception) {
             return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
     }
    } // 게시글 삭제 작성자만 가능
}
