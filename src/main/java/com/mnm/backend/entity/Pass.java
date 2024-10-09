package com.mnm.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Passes")
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //횟수권 구매 여부
    @Column(nullable = false)
    private Boolean passHolder;

    @Column(nullable = true)
    private LocalDate passStartDate;

    @Column(nullable = true)
    private LocalDate passEndDate;
}