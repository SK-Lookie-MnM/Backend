package com.mnm.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class LoginRequestDto {
    private String loginId;
    private String password;

}
