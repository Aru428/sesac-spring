package com.sesac.sesacspring.jpa.service;

import com.sesac.sesacspring.jpa.DTO.StudentDTO;
import com.sesac.sesacspring.jpa.entity.Student;
import com.sesac.sesacspring.jpa.repository.StudentRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepositroy studentRepository;

    public List<StudentDTO> getStudentAll(){
        List<Student> result = studentRepository.findAll();
        List<StudentDTO> students = new ArrayList<>();

        for ( Student student: result ){
            // Builder : 객체를 만들 때 순서에 의해 생기는 문제,
            //           명시적이지 못한 문제 를 해결하기 위해 나온 방법
            // 생성자 주입 : 여러개의 필드가 있을 때 순서를 지켜줘야 한다.
            // setter : 필드 개수만큼 매번 메소드를 만들어줘야 한다.
            StudentDTO studentDTO = StudentDTO.builder() // new Builder
                    .nickname(student.getNickname())
                    .name(student.getName())
                    .build();
            students.add(studentDTO);
        }
        return students;
    }
}