package com.mnm.backend.dto;

import com.mnm.backend.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpResponseDto {
    private String nickname;
    private Role role;
}
