package com.mnm.backend.service;

import com.mnm.backend.dto.QnaDto;
import com.mnm.backend.entity.Qna;
import com.mnm.backend.entity.User;
import com.mnm.backend.exception.QnaNotFoundException;
import com.mnm.backend.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    // list로 출력
    public List<Qna> getAllQnas() {
        return qnaRepository.findAll();
    }

    // id로 조회
    public Qna getQnaById(Long id) {
        return qnaRepository.findById(id)
                .orElseThrow(() -> new QnaNotFoundException("QnA not found with id: " + id));
    }

    public Qna findById(Long id) {
        return qnaRepository.findById(id).orElse(null);
    }

    public Qna createQna(QnaDto qnaDto, User user) {
        Qna qna = new Qna();
        qna.setQuestion(qnaDto.getQuestion());
        qna.setAnswer(qnaDto.getAnswer());
        qna.setUser(user);
        return qnaRepository.save(qna);
    }

    public Qna updateQna(Long id, String answer) {
        Qna qna = getQnaById(id);
        qna.setAnswer(answer);
        qna.setReplied_at(LocalDateTime.now());
        return qnaRepository.save(qna);
    }

}
