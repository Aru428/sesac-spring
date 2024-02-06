package com.sesac.sesacspring.mybatis.mapper;

import com.sesac.sesacspring.mybatis.domain.User;
import com.sesac.sesacspring.mybatis.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    // sql 문을 정의 or xml 파일을 읽거나

    // xml 파일을 읽어서 실행하겠다.
    // read
    List<User> retrieveAll();

    // sql 문 정의
    // create
    @Insert("insert into user(name, nickname) values(#{name}, #{nickname})")
    void createUser(String name, String nickname);
    // void createUser(User user);

    // update
    @Update("update user set nickname = #{nickname} where id = #{id}")
    void updateUser(int id, String nickname);

    // delete
    @Delete("delete from user where id = #{id}")
    void deleteUser(int id);
}
