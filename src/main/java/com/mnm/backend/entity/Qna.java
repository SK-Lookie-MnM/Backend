package com.mnm.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // User 객체를 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QnaStatus status = QnaStatus.PENDING;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime repliedAt;

    public enum QnaStatus {
        PENDING, ANSWERED
    }

}