package com.sesac.sesacspring.jpa.service;

import com.sesac.sesacspring.jpa.DTO.StudentDTO;
import com.sesac.sesacspring.jpa.entity.Student;
import com.sesac.sesacspring.jpa.repository.StudentRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public String insertStudent(String name, String nickname, Student.LoginType type) {
        // 받아 온 데이터로 repository 의 save 메소드를 호출
        Student student = Student.builder().name(name).nickname(nickname).type(type).build();
        Student newStudent = studentRepository.save(student);
        // newStudent : save 를 한 후 반환되는 Entity 객체

        return newStudent.getId() + newStudent.getName();
    }

    public String searchStudentByName(String name) {
        List<Student> student = studentRepository.findByName(name);
//        return "id는 " + student.getId() + " 입니다.";
        return "해당하는 이름의 사용자는 " + student.size() + "명입니다.";
    }

    public String searchStudentById(int id) {
        // Optional<T> : java 8 부터 등장
        // null 일 수도 있는 객체를 감싸는 wrapper 클래스
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("사용자가 없다!"));
        return student.getName();
        // orElse() : 있으면 반환하고 없으면 다른 값 반환
        // orElseThrow() : 있으면 반환하고 없으면 error 처리

//        Optional<Student> student = studentRepository.findById(id);
//        if (student.isPresent()) {
//            // isPresent() : 객체의 여부를 boolean 으로 반환
//            return student.get().getName();
//            // get() : Optional 에 담긴 객체를 반환
//        }
//        return "null";
    }

    public String searchNickname(String nickname) {
        Long result = studentRepository.countByNickname(nickname);
        return "닉네임이 일치하는 사람은 " + result + "명입니다.";
    }

    public String updateStudent(int id, String name) {
        // save(T) : 새로운 entity 를 insert or 기존 entity 를 update
        // T의 기본값(pk)의 상태에 따라 다르게 동작
        // - pk 값이 존재하는 경우 : pk와 연결된 entity 를 update
        // - pk 값이 없는 경우 : 새로운 entity 를 insert
//        Student student = studentRepository.findById(id)
//                .orElseThrow(() -> NoSuchElementException("id is wrong"));

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student s = Student.builder().id(id).name(name).nickname(student.get().getNickname()).type(student.get().getType()).build();
//            Student s = Student.builder().id(id).name(name).build(); // 변경하려는 컬럼만 입력하면 나머지가 null 로 입력됨
            Student updateStudent = studentRepository.save(s);
            return "이름이 " + updateStudent.getName() + " 으로 변경되었습니다.";
        }
        return "존재하지 않는 id 입니다.";
    }
}