package com.sesac.sesacspring.jpa.repository;

import com.sesac.sesacspring.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepositroy<대상으로 지정할 엔티티, 해당 엔티티의 pk 타입>
public interface StudentRepositroy extends JpaRepository<Student, Integer> {

}
