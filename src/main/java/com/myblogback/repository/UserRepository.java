package com.myblog.repository;


import com.myblog.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); //중복 여부
    Optional<User> findByEmail(String email); //이메일로 사용자 조회
}
