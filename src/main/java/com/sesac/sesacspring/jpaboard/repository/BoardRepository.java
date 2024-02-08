package com.sesac.sesacspring.jpaboard.repository;

import com.sesac.sesacspring.jpaboard.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    // title 이 일치 or 검색어가 비어있음
    @Query("select b from BoardEntity b where (b.title=:word or :word='')")
    List<BoardEntity> searchByWord(String word);
}
