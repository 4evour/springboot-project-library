package com.example.rescue.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RescueMapper {
    @Select("SELECT rr.id, rr.user_id AS userId, rr.animal_id AS animalId, rr.title, rr.location, rr.description, rr.status, rr.created_at AS createdAt, u.username AS username, a.name AS animalName FROM rescue_record rr LEFT JOIN sys_user u ON rr.user_id = u.id LEFT JOIN animal a ON rr.animal_id = a.id ORDER BY rr.id DESC")
    List<Map<String, Object>> listAll();

    @Select("SELECT rr.id, rr.user_id AS userId, rr.animal_id AS animalId, rr.title, rr.location, rr.description, rr.status, rr.created_at AS createdAt, a.name AS animalName FROM rescue_record rr LEFT JOIN animal a ON rr.animal_id = a.id WHERE rr.user_id = #{userId} ORDER BY rr.id DESC")
    List<Map<String, Object>> listByUser(@Param("userId") Long userId);

    @Insert("INSERT INTO rescue_record(user_id, animal_id, title, location, description, status) VALUES(#{userId}, #{animalId}, #{title}, #{location}, #{description}, 'SUBMITTED')")
    int insert(@Param("userId") Long userId, @Param("animalId") Long animalId, @Param("title") String title, @Param("location") String location, @Param("description") String description);

    @Update("UPDATE rescue_record SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Delete("DELETE FROM rescue_record WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
