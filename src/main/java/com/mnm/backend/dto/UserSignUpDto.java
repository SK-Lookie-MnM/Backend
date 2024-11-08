package com.mnm.backend.dto;

import com.mnm.backend.entity.Users;
import com.mnm.backend.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;

@Getter
public class UserSignUpDto {

    @NotBlank(message = "이름를 입력해주세요")
    private String name;
    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;
    private String checkedPassword;

    @NotNull(message = "대학교를 선택해주세요")
    private String university;

    @NotNull(message = "회원유형을 선택해주세요")
    private Role role;

    private Boolean isCheckedId;
    private Boolean isCheckedEmail;

    public Users toEntityWithEncodedPassword(String encodedPassword){
        return Users.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .email(email)
                .role(role)
                .name(name)
                .university(university)
                .nickname(setNickname())
                .build();
    }


    private String setNickname () {
        String nickname = name+ "(" + university + "_MNM)";
        return nickname;
    }



}
