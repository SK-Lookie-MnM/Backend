package com.mnm.backend.service;

import com.mnm.backend.dto.UserSignUpDto;
import com.mnm.backend.dto.UserSignUpResponseDto;
import com.mnm.backend.entity.Users;
import com.mnm.backend.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean existsByUsersId(String loginId) throws Exception {
        return usersRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean existsByEmail(String email) throws Exception {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public UserSignUpResponseDto signUp(UserSignUpDto userSignUpDto) throws Exception {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userSignUpDto.getPassword());
        Users users = userSignUpDto.toEntityWithEncodedPassword(encodedPassword);

        usersRepository.save(users);

        return new UserSignUpResponseDto(users.getNickname(), users.getRole());
    }
}
