package com.mnm.backend.repository;

import com.mnm.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// User 엔티티를 관리하는 리포지토리 인터페이스
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long userId); // 기본 제공 메서드지만 명시적으로 선언 가능
    Optional<Users> findByEmail(String email);
    Optional<Users> findByLoginId(String loginId);


    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);


}