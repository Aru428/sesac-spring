package com.sesac.sesacspring.controller;

import com.sesac.sesacspring.dto.UserDTO;
import com.sesac.sesacspring.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
// @RestController // @Controller + @ResponseBody
public class MainController {
    @GetMapping("/")
    public String getMain() {
        return "request";
    }

    // === GET ===
    // 매개변수 넘겨 받는 방법
    // 1. /test?id=123 -> @RequestParam
    // 2. /test/123 -> @PathVariable

    @GetMapping("/get/response1")
    // ?key=value
    // 실제 사용 > ?name=name1
    // @RequestParam 는 기본값으로 required=true
    public String getResponse1(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "response";
    }

    @GetMapping("/get/response2")
    // ?search=검색어
    // ?search=검색어&hashtag=코딩
    public String getResponse2(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "response";
    }

    /*
     * @PathVariable 어노테이션
     * - /test/{id} 형식의 URL 경로로 데이터를 넘겨줄 때 받는 방법
     * - 기본적으로 경로 변수의 값을 필수로 받아야 하기 때문 ( 보내지 않으면 404 error )
     * */
    @GetMapping("/get/response3/{param1}/{param2}")
    // url 안에 넣을때 @PathVariable
    // -> url 경로 안에 있는 것을 변수로 사용함
    public String getResponse3(@PathVariable String param1,
                               @PathVariable(value = "param2") String age,
                               Model model) {
        model.addAttribute("name", param1);
        model.addAttribute("age", age);
        return "response";
    }

    // @PathVariable 을  보낼 때 선택적으로 처리해야 한다면
    @GetMapping({"/get/response4/{param1}","/get/response4/{param1}/{param2}"})
    public String getResponse4(
            @PathVariable String param1,
            @PathVariable(required = false, value = "param2") String age,
            Model model) {
        // 중요! optional 한 parameter 은 맨 뒤에 오도록 설정
        model.addAttribute("name", param1);
        model.addAttribute("age", age);
        return "response";
    }

    // post 방식 - @RequestParam
    @PostMapping("/post/response1")
    public String postResponse1(@RequestParam(value = "name") String a,
                                @RequestParam(value = "age") String b,
                                Model model) {
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return "response";
    }

    @PostMapping("/post/response2")
    public String postResponse2(@RequestParam(value = "name", required = false) String a,
                                @RequestParam(value = "age", required = false) String b,
                                Model model) {
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return "response";
    }

    // @ResponseBody
    // - 응답 시 객체를 json 형태로 리턴한다. (직렬화)
    // express res.send 와 동일
    @PostMapping("/post/response3")
    @ResponseBody
    public String postResponse3(@RequestParam(value = "name", required = false) String a,
                                @RequestParam(value = "age", required = false) String b,
                                Model model) {
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return "response";
    }

    // 실습. API-GET
    @GetMapping("/introduce/{name}")
    public String getPractice(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "response";
    }

    @GetMapping("/introduce2")
    public String getPractice2(@RequestParam String name,
                               @RequestParam int age,
                               Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "response";
    }

    @GetMapping("/practice")
    public String getPractice() {
        return "practice";
    }

    @PostMapping("/practiceInfo")
    public String getUser(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String gender,
                          @RequestParam(required = false) int year,
                          @RequestParam(required = false) int month,
                          @RequestParam(required = false) int day,
                          @RequestParam(required = false) String interest,
                          Model model) {
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("interest", interest);

        return "result";
    }

    @GetMapping("/dto/response1")
    @ResponseBody
    public String dtoResponse1(@ModelAttribute UserDTO userDTO) {
        // DTO : getter 와 setter 가 있는 객체
        // Get 방식에서 DTO 객체로 담아서 값이 받아짐
        // @ModelAttribute : HTML 폼 데이터를 컨트롤러로 전달할 때 객체에 매핑
        // 매핑 = setter 함수 실행
        // ex) ?name=홍길동&age=10 -> setName("홍길동") setAge(10)
        return userDTO.getName() + " " + userDTO.getAge();
    }

    // @RequestBody : 요청의 본문에 있는 데이터(body)를 받는 친구
    @GetMapping("/dto/response11")
    @ResponseBody
    public String dtoResponse11(@RequestBody UserDTO userDTO) {
        return userDTO.getName() + " " + userDTO.getAge();
    }

    // form 파일 업로드 할 때 <form enctype="multipart/form-data">
    // 일반 폼 전송 -> www-x-form-urlencoded => 쿼리 매개변수로 인식
    // 일반 폼 전송 -> RequestBody 로 값을 받을 수 X
    // RequestBody 는 요청의 본문에 있는 데이터(body)를 처리할 수 있기 때문에
    // json, xml 일 때만 실행 가능

    // 일반 폼 전송 - DTO(getter, setter 모두 있는 친구)
    // 1) 어노테이션 없이 DTO 로 받을 경우 -> O
    // 2) @ModelAttribute DTO 받을 경우 -> O
    // 3) @RequestBody DTO 받을 경우 -> 오류

    // 일반 폼 전송은 www-x-form-urlencoded 형식이기 때문에
    // get 이든 post 든 요청의 본문에 데이터가 들어가는 게 아닌 폼 데이터 형태로
    // url 로 데이터가 전송됨. -> 즉, 일반 폼 전송은 RequestBody 사용 불가


    // 일반 폼 전송 - VO
    @GetMapping("/vo/response1")
    @ResponseBody
    public String voResponse1(UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    } // -> null

    @PostMapping("/vo/response2")
    @ResponseBody
    public String voResponse2(UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    } // -> null

    @PostMapping("/vo/response3")
    @ResponseBody
    public String voResponse3(@RequestBody UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    } // -> x (오류 발생)

    // < axios 를 이용한 데이터 처리 >
    @GetMapping("/axios/response1")
    @ResponseBody
    public String axiosResponse1(@RequestParam String name,
                                 @RequestParam String age) {
        return name + " " + age;
    } // -> 1. axios - get - @RequestParam -> o

    @GetMapping("/axios/response2")
    @ResponseBody
    public String axiosResponse2(UserDTO userDTO) {
        // @ModelAttribute
        // axios = application/json
        return userDTO.getName() + " " + userDTO.getAge();
    } // 2. axios - get - @ModelAttribute -> o

    @PostMapping("/axios/response3")
    @ResponseBody
    // url 이었는데, axios post 는 url 에 데이터가 x
    // url 에 아무것도 없는데 name, age required=true 기 때문에 에러가 발생
    public String axiosRes3(@RequestParam String name, @RequestParam String age){
        return "이름: " + name + ", 나이: "+ age;
    }

    @PostMapping("/axios/response4")
    @ResponseBody
    public String axiosRes4(UserDTO userDTO){
        return "이름:" + userDTO.getName() + ", 나이: "+ userDTO.getAge();
    }
    // @ModelAttribute 를 이용해 데이터를 보냈을 때 값이 null
    // axios 로 보내면 url 로 데이터를 보내는게 아니라 본문으로 데이터를 보냄
    // 즉, @ModelAttribute 가 값을 볼 수 없음


    @PostMapping("/axios/response5")
    @ResponseBody
    public String axiosRes5(@RequestBody UserDTO userDTO){
        return "이름:" + userDTO.getName() + ", 나이: "+ userDTO.getAge();
    } // axios + post 데이터 -> @RequestBody o


    // ========== VO 이용 with. axios ==========
    @GetMapping("/axios/vo/response1")
    @ResponseBody
    public String axiosVoRes1(@RequestParam String name, @RequestParam String age) {
        return "이름: " + name + ", 나이: " + age;
    }

    @GetMapping("/axios/vo/response2")
    @ResponseBody
    public String axiosVoRes2(UserVO userVO) {
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    @PostMapping("/axios/vo/response3")
    @ResponseBody
    public String axiosVoRes3(@RequestParam String name, @RequestParam String age) {
        return "이름: " + name + ", 나이: " + age;
    }

    @PostMapping("/axios/vo/response4")
    @ResponseBody
    public String axiosVoRes4(UserVO userVO){
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    @PostMapping("/axios/vo/response5")
    @ResponseBody
    public String axiosVoRes5(@RequestBody UserVO userVO){
        // axios post 로 데이터를 보내면 요청의 본문(body)에 데이터가 들어간다.
        // @RequestBody 는 요청의 본문에 있는 데이터를 읽을 수 있다.
        // UserVO 클래스 setter 메소드가 없다.
        // @RequestBody 는 데이터를 각각의 필드(변수)에 직접적으로 값 주입
        // UserVo UserDTO 상관없이 setter 메소드의 유무와 관계없이 변수에 값을 넣을 수 있다.
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    // 실습. 동적폼전송 실습
    @PostMapping("/practice/axios")
    @ResponseBody
    public String axiosVoPrac(@RequestBody UserVO userVO) {
        return userVO.getName() + " 회원가입 성공";
    }
















}
