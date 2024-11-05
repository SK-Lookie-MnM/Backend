/*
package com.mnm.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mnm.backend.entity.Qna;
import com.mnm.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@Rollback(false) // 테스트 후 데이터가 삭제되지 않도록 설정
public class QnaRepositoryTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        Long id = 1L; // 확인하고자 하는 데이터의 ID
        Optional<Qna> result = qnaRepository.findById(id);

        assertThat(result).isPresent(); // 결과가 존재하는지 확인
        assertThat(result.get().getQuestion()).isNotNull(); // 질문 내용이 null이 아님을 확인
        System.out.println("질문: " + result.get().getQuestion()); // 질문 출력
    }

    @Test
    public void testSaveAndFind() {
        // 새로운 사용자 객체 생성
        User user = new User();
        user.setId(1L); // 또는 적절한 ID 값 설정
        // 필요한 다른 필드도 설정
        userRepository.save(user); // 또는 적절한 방법으로 사용자 저장

        // 새로운 Qna 객체 저장
        Qna qna = new Qna();
        qna.setQuestion("테스트 질문");
        qna.setAnswer("테스트 답변");
        qna.setCreatedAt(LocalDateTime.now());
        qna.setRepliedAt(LocalDateTime.now());
        qna.setUser(user); // 사용자 설정
        qnaRepository.save(qna);

        // 저장된 데이터 확인
        Qna foundQna = qnaRepository.findById(qna.getId()).orElse(null);
        assertThat(foundQna).isNotNull();
        assertThat(foundQna.getQuestion()).isEqualTo("테스트 질문");
    }
}
*/
