package com.sesac.sesacspring.jpa.repository;

import com.sesac.sesacspring.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepositroy<대상으로 지정할 엔티티, 해당 엔티티의 pk 타입>
public interface StudentRepositroy extends JpaRepository<Student, Integer> {
    // [메소드 정의 방법]
    // 1. JPA 의 기본 규칙을 따르는 방법
    // findBy컬럼명
    List<Student> findByName(String name);    // SELECT * FROM Student WHERE name = 'name'
    // Student findByName(String name);
    // 이름이 겹칠 경우가 있기 때문에 List 로 받는다. 하나만 받는 경우는 primary key, unique key 일 경우

    // 이름과 닉네임이 모두 일치하는 경우
    List<Student> findByNameAndNickname(String name, String nickname);
    // 이름이 일치하거나 닉네임이 일치하는 경우
    List<Student> findByNameOrNickname(String name, String nickname);
    // findByAgeGreaterThanEqual(int age) // age 가 값보다 크거나 같은 경우

    // 2. 직접 쿼리를 작성해서 연결
//    @Query("select s from Student s where s.name = :name")
    @Query(nativeQuery = true, value = "select * from Student where name = :a")
    List<Student> findTest(String a);

    Long countByNickname(String nickname);

}
