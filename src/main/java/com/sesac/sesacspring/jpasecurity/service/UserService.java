package com.sesac.sesacspring.jpasecurity.service;

import com.sesac.sesacspring.jpasecurity.entity.UserEntity;
import com.sesac.sesacspring.jpasecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserEntity create(UserEntity userEntity) { // 회원가입 할 때 사용하는 메소드
        if(userEntity == null) {
            throw new RuntimeException("entity null");
        }

        // 중복 이메일 불가
        String email = userEntity.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("exist email");
        }

        return userRepository.save(userEntity);
    }

    // [before] 암호화 적용 전
//    public UserEntity login(String email, String password) { // 로그인 할 때 사용하는 메소드
//        return userRepository.findByEmailAndPassword(email, password);
//    }

    // [after] 암호화 적용 후
    public UserEntity login(String email, String password) {
        UserEntity searchUser = userRepository.findByEmail(email);

        // 비밀번호 일치하는지 확인 : passwordEncoder.matches(실제 비밀번호, 암호화 된 비밀번호)
        if (searchUser != null && passwordEncoder.matches(password, searchUser.getPassword())) {
            return searchUser;
        }
        return null;
    }
}
