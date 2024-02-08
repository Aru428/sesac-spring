package com.sesac.sesacspring.jpaboard.controller;

import com.sesac.sesacspring.jpaboard.dto.BoardDTO;
import com.sesac.sesacspring.jpaboard.service.BoardJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/jpa")
public class BoardJpaController {
    // 5개의 method
    // 1. 전체 조회 -> board.html 렌더링
    // 2. 작성 ( create ) : axios ( 동적폼전송, post ) = @RequestBody
    // 3. 수정 ( update ) : axios ( 동적폼전송, patch )
    // 4. 삭제 ( delete ) : axios ( 동적폼전송, delete )
    // 5. 검색(조회) : axios ( 동적폼전송, get )

    @Autowired
    BoardJpaService boardService;

    // 1. 전체 조회
    @GetMapping("") // /board/mybatis
    public String getBoard(Model model){
        List<BoardDTO> result = boardService.getBoardAll();
        model.addAttribute("list", result);
        return "boardjpa";
    }

    @PostMapping("") // /board/mybatis
    @ResponseBody // 응답을 주기 위함
    public boolean insertBoard(@RequestBody BoardDTO boardDTO) {
        // 2. 게시글 작성
        boardService.insertBoard(boardDTO);
        return true;
    }

    @PatchMapping("") // /board/mybatis
    @ResponseBody
    // 템플릿 파일을 보여주는데, void라면 현재 tempalte을 그대로 다시 보여준다.
    public void patchBoard(@RequestBody BoardDTO boardDTO) {
        boardService.patchBoard(boardDTO);
    }

    @DeleteMapping("")
    @ResponseBody
    public void deleteBoard(@RequestParam int id){
        boardService.deleteBoard(id);
    }

    @GetMapping("/search") // /board/mybatis/search
    @ResponseBody
    public int searchBoard(@RequestParam String word){
        return boardService.searchBoard(word);
    }
}
