package com.sesac.sesacspring.mybatis.controller;

import com.sesac.sesacspring.mybatis.DTO.UserDTO;
import com.sesac.sesacspring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Mybatis
@RestController
@RequestMapping("/user") // -> url : /user
public class UserController {
    // C, R
    // 1. Table 생성 완료 (user)
    // 2. domain 만들기 (domain/user)
    // 3. mapper 만들기
    // 4. service 만들기
    // 5. controller 만들기

    @Autowired
    UserService userService;

    @GetMapping("/all") // -> url : /user/all
    public List<UserDTO> getUser()  {
        List<UserDTO> result = userService.retrieveAll();
        return result;
    }

    @GetMapping("/user") // -> url : /user/user
    public String getUserInsert(@RequestParam String name, @RequestParam String nickname) {
        userService.createUser(name, nickname);
        return "Success";
    }

    @GetMapping("/update")
    public String updateUserNickname(@RequestParam int id, @RequestParam String nickname) {
        userService.updateUser(id, nickname);
        return "nickname update success!";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return "delete success!";
    }
}
