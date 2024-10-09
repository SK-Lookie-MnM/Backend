package com.mnm.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class QnaDto {
        private Long id;
        private Long userId;
        private String question;
        private String answer;
        private LocalDateTime createdAt;
        private LocalDateTime repliedAt;
        //FAQ라 필요없어서 생략해야할듯
        private String status;
}
