package com.sesac.sesacspring.mapper;

import com.sesac.sesacspring.domain.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    // create
    @Insert("insert into board(title, content, writer) values(#{title}, #{content}, #{writer})")
    void createPost(String title, String content, String writer);
    // void createPost(Board board);

    // read
    @Select("select * from board")
    List<Board> getBoard();

    @Select("select * from board where title like '%${word}%'")
    List<Board> getTitleBoard(String word);

    // update
    @Update("update board set title=#{title}, content=#{content}, writer=#{writer} where id = #{id}")
    void updatePost(int id, String title, String content, String writer);

    // delete
    @Delete("delete from board where id = #{id}")
    void deletePost(int id);
}
