package com.mnm.backend.controller;


import com.mnm.backend.dto.UserSignUpDto;
import com.mnm.backend.dto.UserSignUpResponseDto;
import com.mnm.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    // 생성자를 통해 UserService 주입
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignUpDto userSignUpDto, BindingResult bindingResult) throws Exception {
        log.debug("Received signup request with data: {}", userSignUpDto);
        log.debug("isCheckedId: {}", userSignUpDto.getIsCheckedId());
        log.debug("isCheckedEmail: {}", userSignUpDto.getIsCheckedEmail());
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 메시지 출력
            bindingResult.getAllErrors().forEach(error ->
                    System.out.println("Validation error: " + error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if(!userSignUpDto.getIsCheckedId()) {
            log.debug("ID verification not completed");
            throw new BadRequestException("아이디 중복 확인을 완료해주세요.");
        }

        if(!userSignUpDto.getIsCheckedEmail()) {
            log.debug("Email verification not completed");
            throw new BadRequestException("이메일 중복 확인을 완료해주세요.");
        }

        UserSignUpResponseDto userSignUpResponseDto = userService.signUp(userSignUpDto);
        log.info("User signup successful for user: {}", userSignUpDto.getLoginId());
        return ResponseEntity.ok(userSignUpResponseDto);
    }

    @GetMapping("/api/users/checkLoginId")
    public ResponseEntity<String> checkLoginId(String loginId) throws Exception {

        if(userService.existsByUsersId(loginId)) {
            throw new BadRequestException("이미 사용중인 아이디 입니다.");
        } else {
            return ResponseEntity.ok("사용가능한 아이디 입니다.");
        }
    }

    @GetMapping("/api/users/checkEmail")
    public ResponseEntity<String> checkEmail(String email) throws Exception {

        if(userService.existsByEmail(email)) {
            throw new BadRequestException("이미 사용중인 이메일 입니다.");
        } else {
            return ResponseEntity.ok("사용가능한 이메일 입니다.");
        }
    }



}
