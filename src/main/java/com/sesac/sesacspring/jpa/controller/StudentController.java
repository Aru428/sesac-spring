package com.sesac.sesacspring.jpa.controller;

import com.sesac.sesacspring.jpa.DTO.StudentDTO;
import com.sesac.sesacspring.jpa.entity.Student;
import com.sesac.sesacspring.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
//    @GetMapping("/count")
//    public int getCountAll(){}

    // 1. 전체 검색 ( SELECT * FROM Student )
    @GetMapping("/all")
    public List<StudentDTO> getAll(){
        // student의 목록을 전부 가져와서 보여주는 api
        List<StudentDTO> result = studentService.getStudentAll();
        return result;
//        return studentService.getStudentAll();
    }

    // 2. 삽입 ( INSERT INTO ~~~ )
    @GetMapping("/insert")  // /student/insert?name=이름&nickname=닉넴임&type=타입
    public String insertStudent(@RequestParam String name,
                                @RequestParam String nickname,
                                @RequestParam Student.LoginType type) {
        return studentService.insertStudent(name, nickname, type);
    }

    // 3. 조건에 따른 검색 ( SELECT * FROM Student WHERE name = '' )
    @GetMapping("/search/name")
    public String searchStudentByName(@RequestParam String name) {
        return studentService.searchStudentByName(name);
    }

    // 4. 조건에 따른 검색 ( SELECT * FROM Student WHERE id = '' )
    @GetMapping("/search/id")
    public String searchStudentById(@RequestParam int id) {
        return studentService.searchStudentById(id);
    }

//    @GetMapping("/search")
//    public ? getSearch(@RequestBody int id){}

    // [실습]
    // 1) count 이용해서 "?nickname=값" 일치하는 친구 몇 명인지 가져오기
    @GetMapping("/search/count")
    public String searchNickname(@RequestParam String nickname) {
        return studentService.searchNickname(nickname);
    }

    // 2) "?id={id}&name={name}" 보냈을 때 id 값의 튜플의 name 컬럼을 변경하기 (update)
    @GetMapping("/update")
    public String updateStudent(@RequestParam int id, @RequestParam String name) {
        return studentService.updateStudent(id, name);
    }

}