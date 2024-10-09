package com.mnm.backend.repository;

import com.mnm.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    //단순 CRUD 메서드 사용
}
