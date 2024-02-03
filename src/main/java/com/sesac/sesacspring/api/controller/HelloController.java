package com.sesac.sesacspring.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
// @Controller : 해당 클래스가 Controller 의 역할을 하는 클래스라는 것을 Spring Container 에게 알려준다.
public class HelloController {
    @GetMapping("/hi")
    // URL 을 매핑시켜주는 친구
    // 클라이언트가 /hi 라는 경로로 GET method 로 접근한다면 아래 메소드를 실행시켜라
    public String getHi(Model model) {
        // Model : Controller 안의 메서드가 파라미터로 받을 수 있는 객체 중 하나
        // Model 안에 정보를 담아서 view 로 전달
        // IoC : 개발자가 직접 model 을 생성 X

        model.addAttribute("name", "홍길동");
        model.addAttribute("name2","<strong>코딩온</strong>");
        model.addAttribute("age", 27);

        String[] items = {"a", "b", "c", "d", "e"};
        model.addAttribute("item", items);

        return "hi"; // 템플릿 파일의 이름
        // res.render("hi")
        // res.render("hi", {name: "홍길동"}) -> 이렇게 직접 넣어주지 않고, 스프링 컨테이너가 model 을 보내줌
    }

    // 실습. Thymeleaf (2)
    @GetMapping("/people")
    public String getPeople(Model model) {
        ArrayList<Person> people = new ArrayList<>();
        Person p1 = new Person("kim", 10);
        Person p2 = new Person("lee", 20);
        Person p3 = new Person("hong", 30);
        Person p4 = new Person("park", 40);
        Person p5 = new Person("shin", 50);

        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);

        model.addAttribute("people", people);

        Person p = new Person("h", 10);
        System.out.println(p.getName());

        return "people";
    }
}
