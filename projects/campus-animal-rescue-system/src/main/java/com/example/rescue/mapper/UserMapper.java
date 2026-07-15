package com.example.rescue.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    @Select("SELECT id, username, password, real_name AS realName, phone, role, status, created_at AS createdAt FROM sys_user WHERE username = #{username}")
    Map<String, Object> findByUsername(@Param("username") String username);

    @Select("SELECT id, username, password, real_name AS realName, phone, role, status, created_at AS createdAt FROM sys_user WHERE id = #{id}")
    Map<String, Object> findById(@Param("id") Long id);

    @Select("SELECT id, username, real_name AS realName, phone, role, status, created_at AS createdAt FROM sys_user ORDER BY id DESC")
    List<Map<String, Object>> findAll();

    @Insert("INSERT INTO sys_user(username, password, real_name, phone, role, status) VALUES(#{username}, #{password}, #{realName}, #{phone}, #{role}, 'ACTIVE')")
    int insert(@Param("username") String username, @Param("password") String password, @Param("realName") String realName, @Param("phone") String phone, @Param("role") String role);

    @Update("UPDATE sys_user SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Delete("DELETE FROM sys_user WHERE id = #{id} AND role <> 'ADMIN'")
    int deleteNonAdmin(@Param("id") Long id);
}
