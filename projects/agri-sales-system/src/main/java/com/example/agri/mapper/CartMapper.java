package com.example.agri.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CartMapper {
    @Select("SELECT ci.id, ci.user_id AS userId, ci.product_id AS productId, ci.quantity, ci.created_at AS createdAt, p.name AS productName, p.price, p.stock, p.image_url AS imageUrl FROM cart_item ci JOIN product p ON ci.product_id = p.id WHERE ci.user_id = #{userId} ORDER BY ci.id DESC")
    List<Map<String, Object>> listByUser(@Param("userId") Long userId);

    @Insert("INSERT INTO cart_item(user_id, product_id, quantity) VALUES(#{userId}, #{productId}, #{quantity}) ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)")
    int add(@Param("userId") Long userId, @Param("productId") Long productId, @Param("quantity") Integer quantity);

    @Update("UPDATE cart_item SET quantity = #{quantity} WHERE id = #{id} AND user_id = #{userId}")
    int updateQuantity(@Param("id") Long id, @Param("userId") Long userId, @Param("quantity") Integer quantity);

    @Delete("DELETE FROM cart_item WHERE id = #{id} AND user_id = #{userId}")
    int delete(@Param("id") Long id, @Param("userId") Long userId);

    @Delete("DELETE FROM cart_item WHERE user_id = #{userId}")
    int clearByUser(@Param("userId") Long userId);
}
