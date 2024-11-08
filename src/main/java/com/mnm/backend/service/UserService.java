package com.mnm.backend.service;

import com.mnm.backend.dto.UserSignUpDto;
import com.mnm.backend.dto.UserSignUpResponseDto;
import org.springframework.stereotype.Service;

public interface UserService {

    public boolean existsByUsersId(String loginId) throws Exception;
    public boolean existsByEmail(String email) throws Exception;
    // 회원가입
    public UserSignUpResponseDto signUp(UserSignUpDto userSignUpDto) throws Exception;

}
