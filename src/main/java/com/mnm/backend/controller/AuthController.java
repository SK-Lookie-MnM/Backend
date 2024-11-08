package com.mnm.backend.controller;


import com.mnm.backend.auth.jwt.TokenDto;
import com.mnm.backend.auth.jwt.TokenRequestDto;
import com.mnm.backend.dto.LoginRequestDto;
import com.mnm.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/api/users/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        TokenDto tokenDto = authService.login(loginRequestDto);

        return ResponseEntity.ok(tokenDto);
    }


    @PostMapping("/api/users/reissue")
    public ResponseEntity<TokenDto> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {

        TokenDto tokenDto = authService.reissue(tokenRequestDto);

        return ResponseEntity.ok(tokenDto);
    }
}
