package com.mnm.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "Passes")
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    //횟수권 구매 여부
    @Column(nullable = false)
    private Boolean passHolder;

    @Column(nullable = true)
    private LocalDate passStartDate;

    @Column(nullable = true)
    private LocalDate passEndDate;
}