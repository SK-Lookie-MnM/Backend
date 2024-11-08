package com.mnm.backend.service;

import com.mnm.backend.auth.custom.CustomUserDetails;
import com.mnm.backend.entity.Users;
import com.mnm.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    /**
     * [JWT 토큰의 인증 계정 찾기]
     * UserDetailsService의 메서드를 오버라이딩하여 로그인 시, 로그인 아이디로 UsersRepository의 로직으로 인증 계정을 찾음
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * [CustomUserDetail 형태로 반환]
     * 해당 계정 정보가 존재 시, CustomUserDetail의 형태로 리턴
     * @return [CustomUserDetail]
     */
    private CustomUserDetails createUserDetails(Users users) {
        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + users.getRole().toString()));
        return new CustomUserDetails(
                users.getLoginId(),
                users.getPassword(),
                authorities,
                users.getNickname(),
                users.getId());

    }

}
