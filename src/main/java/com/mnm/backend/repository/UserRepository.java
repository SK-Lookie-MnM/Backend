package com.mnm.backend.repository;

import com.mnm.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// User 엔티티를 관리하는 리포지토리 인터페이스
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId); // 기본 제공 메서드지만 명시적으로 선언 가능
}