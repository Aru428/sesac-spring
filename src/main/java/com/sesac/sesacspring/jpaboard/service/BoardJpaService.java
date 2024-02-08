package com.sesac.sesacspring.jpaboard.service;

import com.sesac.sesacspring.jpaboard.domain.Board;
import com.sesac.sesacspring.jpaboard.dto.BoardDTO;
import com.sesac.sesacspring.jpaboard.entity.BoardEntity;
import com.sesac.sesacspring.jpaboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardJpaService {
    @Autowired
    BoardRepository boardRepository;

    public List<BoardDTO> getBoardAll(){
        List<BoardEntity> jpaResult = boardRepository.findAll();
        List<BoardDTO> boards = new ArrayList<>();

        for ( BoardEntity boardEntity : jpaResult ){
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setBoardID(boardEntity.getId());
            boardDTO.setTitle(boardEntity.getTitle());
            boardDTO.setContent(boardEntity.getContent());
            boardDTO.setWriter(boardEntity.getWriter());
            boardDTO.setRegistered(
                    new SimpleDateFormat("yyyy-mm-dd").format(boardEntity.getRegistered())
            );
            // 1) dto 의 값을 timestamp 변경
            // 2) timestamp -> string 파싱 (SimpleDateFormat)
            boardDTO.setNo(100 + boardEntity.getId());
            boards.add(boardDTO);
        }
        return boards;
    }
    public boolean insertBoard(BoardDTO boardDTO) {
        // save()
        BoardEntity boardEntity = BoardEntity.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        boardRepository.save(boardEntity);
        return true;
    }

    public void patchBoard(BoardDTO boardDTO) {
        // save()
        // board.getBoardID // title, content, writer
        BoardEntity boardEntity = boardRepository.findById(boardDTO.getBoardID())
                .orElseThrow(() -> new NoSuchElementException("board patch : id is wrong"));

        BoardEntity modified = BoardEntity.builder()
                .id(boardEntity.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .registered(boardEntity.getRegistered())
                .build();
        boardRepository.save(modified);
    }
    public void deleteBoard(int id){
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("board delete : id is wrong"));

        boardRepository.delete(boardEntity);
        // boardRepository.deleteById(id); 도 가능 그러나 entity 로 삭제하는 것이 더 정확하다.
    }

    public int searchBoard(String word) {
        List<BoardEntity> result = boardRepository.searchByWord(word);
        return result.size();
    }
}
