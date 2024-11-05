package com.mnm.backend.auth.util;


import com.mnm.backend.auth.custom.CustomUserDetails;
import com.mnm.backend.auth.jwt.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/*
*
* JWT 토큰 생성, 검증 이루어집니다.
*
* */
@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private Key key;

    // application.yml에서 주입받은 secret 값을 base64 decode하여 key 변수에 할당
    public TokenProvider(@Value("${jwt.security-key}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체에 포함되어 있는 권한 정보들을 담은 토큰을 생성
    public TokenDto generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        // Authentication에서 CustomUserDetails 가져오기
        // 만약 CustomUserDetails를 사용한다면, getPrincipal()은 이 객체를 반환합니다.
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long usersId = userDetails.getUsersId();
        String nickname = userDetails.getNickname();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .claim("usersId", usersId)                  // 사용자 ID 클레임 추가
                .claim("nickname", nickname)               // 닉네임 클레임 추가
                .setExpiration(accessTokenExpiresIn)       // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512)   // 서명 header "alg": "HS512"
                .compact();



        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();

    }


    // JWT토큰을 복호화하여 토큰에 들어있는 정보를 꺼냅니다.
    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화 : JWT의 body
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Long usersId = claims.get("usersId", Long.class);
        String nickname = claims.get("nickname", String.class);

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        CustomUserDetails principal = new CustomUserDetails(
                claims.getSubject(),  // username
                "",                   // password는 빈 값으로 설정
                authorities,          // 권한 정보
                nickname,             // 닉네임
                usersId                // 사용자 ID
        );

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    // 토큰을 검증하는 역할
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서면입니다.");
        }catch(ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        }catch(UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT토큰입니다.");
        }catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
