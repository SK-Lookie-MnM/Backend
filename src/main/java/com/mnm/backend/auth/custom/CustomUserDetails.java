package com.mnm.backend.auth.custom;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final String nickname;  //닉네임
    private final Long usersId;    //PK

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                            String nickname, Long usersId) {
        super(
                username, password, authorities
        );
        this.nickname = nickname;
        this.usersId = usersId;
    }


}
