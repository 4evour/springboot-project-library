package com.example.rescue.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AnimalMapper {
    @Select("SELECT id, name, species, gender, age, location, health_status AS healthStatus, description, image_url AS imageUrl, status, created_at AS createdAt FROM animal ORDER BY id DESC")
    List<Map<String, Object>> list();

    @Select("SELECT id, name, species, gender, age, location, health_status AS healthStatus, description, image_url AS imageUrl, status, created_at AS createdAt FROM animal WHERE id = #{id}")
    Map<String, Object> findById(@Param("id") Long id);

    @Insert("INSERT INTO animal(name, species, gender, age, location, health_status, description, image_url, status) VALUES(#{name}, #{species}, #{gender}, #{age}, #{location}, #{healthStatus}, #{description}, #{imageUrl}, #{status})")
    int insert(@Param("name") String name, @Param("species") String species, @Param("gender") String gender, @Param("age") String age, @Param("location") String location, @Param("healthStatus") String healthStatus, @Param("description") String description, @Param("imageUrl") String imageUrl, @Param("status") String status);

    @Update("UPDATE animal SET name = #{name}, species = #{species}, gender = #{gender}, age = #{age}, location = #{location}, health_status = #{healthStatus}, description = #{description}, image_url = #{imageUrl}, status = #{status} WHERE id = #{id}")
    int update(@Param("id") Long id, @Param("name") String name, @Param("species") String species, @Param("gender") String gender, @Param("age") String age, @Param("location") String location, @Param("healthStatus") String healthStatus, @Param("description") String description, @Param("imageUrl") String imageUrl, @Param("status") String status);

    @Select("SELECT (SELECT COUNT(*) FROM rescue_record WHERE animal_id = #{id}) + (SELECT COUNT(*) FROM adoption_application WHERE animal_id = #{id})")
    int countReferences(@Param("id") Long id);

    @Delete("DELETE FROM animal WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
