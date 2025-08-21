// Auth(인증) 로그인/로그아웃 처리
package com.myblog.controller;

import com.myblog.common.session.SessionConst;
import com.myblog.domain.entity.User;
import com.myblog.dto.LoginRequest;
import com.myblog.dto.SignupRequest;
import com.myblog.dto.UserResponse;
import com.myblog.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService; // http세션 기반 인증

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            return ResponseEntity.ok(UserResponse.from(userService.signup(request)));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(409).body(Map.of("message", exception.getMessage()));
        }
    } // 회원가입 성공시 200 + userresponse / 실패시 409

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        try {
            User user = userService.verifyLogin(request);
            session.setAttribute(SessionConst.USER_ID, user.getId()); // 세션에 사용자 id 저장
            return ResponseEntity.ok(UserResponse.from(user));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(401).body(Map.of("message", exception.getMessage()));
        } // 로그인 성공시 200 + userresponse / 실패시 401
    }
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        // 1) 세션에서 로그인한 사용자 ID 가져오기
        Long userId = (Long) session.getAttribute(SessionConst.USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }
        // 2) DB에서 현재 사용자 조회
        User currentUser = userService.getById(userId);
        if (currentUser == null) {
            return ResponseEntity.status(404).body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }
        // 3) UserResponse DTO로 변환 후 반환
        return ResponseEntity.ok(UserResponse.from(currentUser));
    }
}
