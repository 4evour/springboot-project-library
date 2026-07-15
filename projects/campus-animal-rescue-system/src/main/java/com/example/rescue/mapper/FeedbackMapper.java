package com.example.rescue.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FeedbackMapper {
    @Select("SELECT f.id, f.user_id AS userId, f.content, f.reply, f.created_at AS createdAt, u.username AS username FROM feedback f LEFT JOIN sys_user u ON f.user_id = u.id ORDER BY f.id DESC")
    List<Map<String, Object>> listAll();

    @Select("SELECT f.id, f.user_id AS userId, f.content, f.reply, f.created_at AS createdAt FROM feedback f WHERE f.user_id = #{userId} ORDER BY f.id DESC")
    List<Map<String, Object>> listByUser(@Param("userId") Long userId);

    @Insert("INSERT INTO feedback(user_id, content, reply) VALUES(#{userId}, #{content}, NULL)")
    int insert(@Param("userId") Long userId, @Param("content") String content);

    @Update("UPDATE feedback SET reply = #{reply} WHERE id = #{id}")
    int reply(@Param("id") Long id, @Param("reply") String reply);

    @Delete("DELETE FROM feedback WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
