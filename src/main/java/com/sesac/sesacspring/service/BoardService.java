package com.sesac.sesacspring.service;

import com.sesac.sesacspring.domain.Board;
import com.sesac.sesacspring.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    // read
    public List<Board> getBoard() {
        List<Board> result = boardMapper.getBoard();
        return result;
    }

    public List<Board> getTitleBoard(String word) {
        List<Board> result = boardMapper.getTitleBoard(word);
        return result;
    }

    // create
    public void createPost(String title, String content, String writer) {
        boardMapper.createPost(title, content, writer);
    }

    // update
    public void updatePost(int id, String title, String content, String writer) {
        boardMapper.updatePost(id, title, content, writer);
    }

    // delete
    public void deletePost(int id) {
       boardMapper.deletePost(id);
    }
}
