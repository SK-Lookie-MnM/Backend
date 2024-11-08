package com.mnm.backend.service;


import com.mnm.backend.auth.jwt.TokenDto;
import com.mnm.backend.auth.jwt.TokenRequestDto;
import com.mnm.backend.dto.LoginRequestDto;
import org.springframework.stereotype.Service;


public interface AuthService {

    public TokenDto login(LoginRequestDto loginRequestDto);
    public TokenDto reissue (TokenRequestDto tokenRequestDto);
}
