package com.example.agri.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductMapper {
    @Select("SELECT id, name, description, created_at AS createdAt FROM product_category ORDER BY id DESC")
    List<Map<String, Object>> listCategories();

    @Insert("INSERT INTO product_category(name, description) VALUES(#{name}, #{description})")
    int insertCategory(@Param("name") String name, @Param("description") String description);

    @Update("UPDATE product_category SET name = #{name}, description = #{description} WHERE id = #{id}")
    int updateCategory(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

    @Select("SELECT COUNT(*) FROM product WHERE category_id = #{categoryId}")
    int countProductsByCategory(@Param("categoryId") Long categoryId);

    @Delete("DELETE FROM product_category WHERE id = #{id}")
    int deleteCategory(@Param("id") Long id);

    @Select("<script>SELECT p.id, p.category_id AS categoryId, p.name, p.description, p.price, p.stock, p.image_url AS imageUrl, p.status, p.created_at AS createdAt, c.name AS categoryName FROM product p LEFT JOIN product_category c ON p.category_id = c.id <where><if test='categoryId != null'>p.category_id = #{categoryId}</if><if test='keyword != null and keyword != \"\"'> AND p.name LIKE CONCAT('%', #{keyword}, '%')</if></where> ORDER BY p.id DESC</script>")
    List<Map<String, Object>> listProducts(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);

    @Select("SELECT p.id, p.category_id AS categoryId, p.name, p.description, p.price, p.stock, p.image_url AS imageUrl, p.status, p.created_at AS createdAt, c.name AS categoryName FROM product p LEFT JOIN product_category c ON p.category_id = c.id WHERE p.id = #{id}")
    Map<String, Object> findProduct(@Param("id") Long id);

    @Insert("INSERT INTO product(category_id, name, description, price, stock, image_url, status) VALUES(#{categoryId}, #{name}, #{description}, #{price}, #{stock}, #{imageUrl}, #{status})")
    int insertProduct(@Param("categoryId") Long categoryId, @Param("name") String name, @Param("description") String description, @Param("price") BigDecimal price, @Param("stock") Integer stock, @Param("imageUrl") String imageUrl, @Param("status") String status);

    @Update("UPDATE product SET category_id = #{categoryId}, name = #{name}, description = #{description}, price = #{price}, stock = #{stock}, image_url = #{imageUrl}, status = #{status} WHERE id = #{id}")
    int updateProduct(@Param("id") Long id, @Param("categoryId") Long categoryId, @Param("name") String name, @Param("description") String description, @Param("price") BigDecimal price, @Param("stock") Integer stock, @Param("imageUrl") String imageUrl, @Param("status") String status);

    @Update("UPDATE product SET stock = stock - #{quantity} WHERE id = #{id} AND stock >= #{quantity}")
    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    @Select("SELECT (SELECT COUNT(*) FROM cart_item WHERE product_id = #{id}) + (SELECT COUNT(*) FROM order_item WHERE product_id = #{id})")
    int countProductReferences(@Param("id") Long id);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteProduct(@Param("id") Long id);
}
