package com.example.rescue.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdoptionMapper {
    @Select("SELECT aa.id, aa.user_id AS userId, aa.animal_id AS animalId, aa.reason, aa.status, aa.created_at AS createdAt, u.username AS username, a.name AS animalName FROM adoption_application aa LEFT JOIN sys_user u ON aa.user_id = u.id LEFT JOIN animal a ON aa.animal_id = a.id ORDER BY aa.id DESC")
    List<Map<String, Object>> listAll();

    @Select("SELECT aa.id, aa.user_id AS userId, aa.animal_id AS animalId, aa.reason, aa.status, aa.created_at AS createdAt, a.name AS animalName FROM adoption_application aa LEFT JOIN animal a ON aa.animal_id = a.id WHERE aa.user_id = #{userId} ORDER BY aa.id DESC")
    List<Map<String, Object>> listByUser(@Param("userId") Long userId);

    @Insert("INSERT INTO adoption_application(user_id, animal_id, reason, status) VALUES(#{userId}, #{animalId}, #{reason}, 'PENDING')")
    int insert(@Param("userId") Long userId, @Param("animalId") Long animalId, @Param("reason") String reason);

    @Update("UPDATE adoption_application SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Delete("DELETE FROM adoption_application WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
