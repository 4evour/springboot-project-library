package com.example.agri.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {
    @Insert("INSERT INTO orders(user_id, order_no, receiver_name, receiver_phone, address, total_amount, status) VALUES(#{userId}, #{orderNo}, #{receiverName}, #{receiverPhone}, #{address}, #{totalAmount}, 'PENDING')")
    int insertOrder(@Param("userId") Long userId, @Param("orderNo") String orderNo, @Param("receiverName") String receiverName, @Param("receiverPhone") String receiverPhone, @Param("address") String address, @Param("totalAmount") BigDecimal totalAmount);

    @Select("SELECT LAST_INSERT_ID()")
    Long lastInsertId();

    @Insert("INSERT INTO order_item(order_id, product_id, product_name, price, quantity, subtotal) VALUES(#{orderId}, #{productId}, #{productName}, #{price}, #{quantity}, #{subtotal})")
    int insertItem(@Param("orderId") Long orderId, @Param("productId") Long productId, @Param("productName") String productName, @Param("price") BigDecimal price, @Param("quantity") Integer quantity, @Param("subtotal") BigDecimal subtotal);

    @Select("SELECT o.id, o.user_id AS userId, o.order_no AS orderNo, o.total_amount AS totalAmount, o.receiver_name AS receiverName, o.receiver_phone AS receiverPhone, o.address, o.status, o.created_at AS createdAt, u.username AS username FROM orders o JOIN sys_user u ON o.user_id = u.id ORDER BY o.id DESC")
    List<Map<String, Object>> listAll();

    @Select("SELECT id, user_id AS userId, order_no AS orderNo, total_amount AS totalAmount, receiver_name AS receiverName, receiver_phone AS receiverPhone, address, status, created_at AS createdAt FROM orders WHERE user_id = #{userId} ORDER BY id DESC")
    List<Map<String, Object>> listByUser(@Param("userId") Long userId);

    @Select("SELECT id, order_id AS orderId, product_id AS productId, product_name AS productName, price, quantity, subtotal FROM order_item WHERE order_id = #{orderId}")
    List<Map<String, Object>> listItems(@Param("orderId") Long orderId);

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
