package com.sesac.sesacspring.jpasecurity.controller;

import com.sesac.sesacspring.jpasecurity.dto.UserDTO;
import com.sesac.sesacspring.jpasecurity.entity.UserEntity;
import com.sesac.sesacspring.jpasecurity.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j // 로그 관련 메소드를 편리하게 사용할 수 있음.
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public String getAuth(){
        return "GET /auth";
    }

    @PostMapping("/signup")
    // ? : 와일드 카드 (어떤 값을 body 에 담을지 모름)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 암호화
                    .build();

            UserEntity responseUser = userService.create(user);

            // 사용자가 입력한 PW를 보내지 않기 위해 DTO 생성
            // 요청을 주고 받을 때는 DTO 를 사용하는 것이 좋다.
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(responseUser.getEmail())
                    .username(responseUser.getUsername())
                    .id(responseUser.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            // 상태코드 400 : 사용자가 보낸 값이 잘못됐다.
            // 500 : 서버 오류
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(HttpSession session, @RequestBody UserDTO userDTO) {
        try {
            UserEntity user = userService.login(userDTO.getEmail(), userDTO.getPassword());

            if (user == null) {
                throw new RuntimeException("login failed");
            }

            UserDTO responseUserDTO =  UserDTO.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .build();

            // log.info()
            // log.error()
             log.warn("session id {}", session.getId());
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
