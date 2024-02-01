package com.sesac.sesacspring.controller;

import com.sesac.sesacspring.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class UserController {
    HashMap<String, Person> users = new HashMap<>();

    // 회원가입 페이지 렌더
    @GetMapping("/users")
    public String getSignUpPage() {
        return "users";
    }

    // 회원가입
    @PostMapping("/users/auth")
    @ResponseBody
    public String postUser(UserDTO userDTO) {
        if (users.containsKey(userDTO.getName()))
            return "이미 등록된 회원입니다.";
        else {
            users.put(userDTO.getName(), new Person(userDTO.getName(), userDTO.getAge()));
            return userDTO.getName() + "님 회원가입 성공!";
        }
    }

    // 로그인
    @PostMapping("/users/login")
    @ResponseBody
    public String postLogin(UserDTO userDTO) {
        if (users.containsKey(userDTO.getName())) {
            Person user = users.get(userDTO.getName());
            if (user.getAge() == userDTO.getAge()) { // age 가 password 라고 가정
                return userDTO.getName() + "님 환영합니다!";
            } else {
                return "비밀번호가 잘못 입력되었습니다.";
            }
        } else {
            return "존재하지 않는 ID 입니다.";
        }
    }
}
