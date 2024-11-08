/*
package com.mnm.backend.controller;

import com.mnm.backend.dto.QnaDto;
import com.mnm.backend.entity.Qna;
import com.mnm.backend.entity.User;
import com.mnm.backend.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

    @Autowired
    private QnaService qnaService;

    // list로 출력
    @GetMapping
    public List<Qna> getAllQnas() {
        return qnaService.getAllQnas();
    }

    // id로 질문 조회
    @GetMapping("/{id}")
    public ResponseEntity<Qna>  getQnaById(@PathVariable Long id) {
        Qna qna = qnaService.findById(id);
        if (qna != null) {
            return ResponseEntity.ok(qna);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Qna createQna(@RequestBody QnaDto qnaDto) {
        // 실제 User 서비스에서 유저 정보를 가져와야 함
        User user = new User();
        user.setId(qnaDto.getUserId());
        return qnaService.createQna(qnaDto, user);
    }

    // id로 답변 조회
    @PutMapping("/{id}/answer")
    public Qna updateQna(@PathVariable Long id, @RequestBody String answer) {
        return qnaService.updateQna(id, answer);
    }

}
*/
