package com.sesac.sesacspring.controller;

import com.sesac.sesacspring.DTO.BoardDTO;
import com.sesac.sesacspring.domain.Board;
import com.sesac.sesacspring.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    // 게시판 페이지 렌더 & 게시글 전체 조회
    @GetMapping("")
    public String getBoard(Model model) {
        List<Board> posts = boardService.getBoard();
        model.addAttribute("posts", posts);
        return "board";
    }

    // create : 게시판 글 작성
    @PostMapping("/post")
    @ResponseBody
    public String createPost(@RequestBody BoardDTO boardDTO) {
        boardService.createPost(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getWriter());
        return "게시글 작성 성공!";
    }

    // read : 검색어와 일치하는 제목의 게시글 조회
    @GetMapping("/search")
    @ResponseBody
    public int getTitleBoard(@RequestParam String word) {
        List<Board> result = boardService.getTitleBoard(word);
        return result.size();
    }

    // update : 게시글 수정
    @PatchMapping("")
    public void updatePost(@RequestBody Board board) {
        // @RequestBody 는 객체로 받는다.
        boardService.updatePost(board.getId(), board.getTitle(), board.getContent(), board.getWriter());
    }

    // delete : 게시글 삭제
    @DeleteMapping("")
    public void deletePost(@RequestParam int id) {
        boardService.deletePost(id);
    }
}
