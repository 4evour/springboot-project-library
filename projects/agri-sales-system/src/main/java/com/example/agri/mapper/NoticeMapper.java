package com.example.agri.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NoticeMapper {
    @Select("SELECT id, title, content, created_at AS createdAt FROM notice ORDER BY id DESC")
    List<Map<String, Object>> list();

    @Insert("INSERT INTO notice(title, content) VALUES(#{title}, #{content})")
    int insert(@Param("title") String title, @Param("content") String content);

    @Update("UPDATE notice SET title = #{title}, content = #{content} WHERE id = #{id}")
    int update(@Param("id") Long id, @Param("title") String title, @Param("content") String content);

    @Delete("DELETE FROM notice WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
