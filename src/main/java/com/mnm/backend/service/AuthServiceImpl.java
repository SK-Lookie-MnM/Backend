package com.mnm.backend.service;

import com.mnm.backend.entity.jwt.RefreshToken;
import com.mnm.backend.auth.jwt.TokenDto;
import com.mnm.backend.auth.jwt.TokenRequestDto;
import com.mnm.backend.auth.util.TokenProvider;
import com.mnm.backend.dto.LoginRequestDto;
import com.mnm.backend.repository.RefreshTokenRepository;
import com.mnm.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 *
 * 1. 로그인 시도된 이메일과 비밀번호를 인증을 처리하기 위한 형태인 UsernamePasswordAuthenticationToken 으로 변환해준다.
 * 2. 실제 인증을 수행하며, Spring Security가 AuthenticationManagerBuilder를 통해 인증을 처리하게 된다. 과정은 아래와 같다.
 *  2-1. CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행되어 사용자가 존재하는지 확인
 *  2-2. loadUserByUsername는 데이터베이스의 유저 정보를 CustomUserDetails 형태로담아 반환
 *  2-3. 반환된 데이터를 바탕으로 AuthenticationProvider가 입력 비밀번호와, 데이터베이스에서 가져온 사용자 정보의 비밀번호를 비교하고, 일치하는지의 여부를 판단하여 인증 결과를 결정
 *
 *
 *
 * */
@Service
public class AuthServiceImpl implements AuthService{

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public AuthServiceImpl(UsersRepository usersRepository, PasswordEncoder encoder, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {

        String loginId = loginRequestDto.getLoginId();
        String password = loginRequestDto.getPassword();

        // 1. Login Id/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Override
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        // 2. Access Token 에서 Authenticatio의 loginId 받아오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 loginId 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 Access 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }


}
