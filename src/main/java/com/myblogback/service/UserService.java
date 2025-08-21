package com.myblogback.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.myblogback.domain.entity.User;
import com.myblogback.dto.LoginRequest;
import com.myblogback.dto.SignupRequest;
import com.myblogback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // 로그인검증 비즈니스 로직 담당

    @Transactional
    public User signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일임");
        }
        String hash = BCrypt.withDefaults().hashToString(12, request.password().toCharArray()); //이게 비밀번호 해시
        User user = User.builder()
                    .email(request.email())
                    .passwordHash(hash)
                    .nickname(request.nickname())
                    .build();
        return userRepository.save(user);
    } // 이메일 중복체크 = 비밀번호 해시 후 저장

    @Transactional(readOnly = true)
    public User verifyLogin(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElse(null);
        if (user == null) throw new IllegalArgumentException("이메일 또는 비번 틀렸음 ㅇㅇ");
        if (!BCrypt.verifyer().verify(request.password().toCharArray(), user.getPasswordHash()).verified) {
            throw new IllegalArgumentException("이메일 또는 비번 틀렸음 ㅇㅇ");
        }
        return user;
    } // 이메일 존재 여부 확인, 비번 해시 검증

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    } // 세션에 저장된 userId로 내 정보 조회 시 사용
}