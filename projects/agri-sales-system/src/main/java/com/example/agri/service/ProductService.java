package com.example.agri.service;

import com.example.agri.mapper.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Map<String, Object>> listCategories() {
        return productMapper.listCategories();
    }

    public void saveCategory(Map<String, Object> input) {
        String name = text(input, "name");
        String description = optional(input, "description");
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
        Long id = longValue(input.get("id"));
        if (id == null) {
            productMapper.insertCategory(name, description);
        } else {
            productMapper.updateCategory(id, name, description);
        }
    }

    public void deleteCategory(Long id) {
        if (productMapper.countProductsByCategory(id) > 0) {
            throw new IllegalArgumentException("分类下已有商品，不能删除");
        }
        productMapper.deleteCategory(id);
    }

    public List<Map<String, Object>> listProducts(Long categoryId, String keyword) {
        return productMapper.listProducts(categoryId, keyword);
    }

    public void saveProduct(Map<String, Object> input) {
        Long categoryId = longValue(input.get("categoryId"));
        String name = text(input, "name");
        BigDecimal price = decimal(input.get("price"));
        Integer stock = intValue(input.get("stock"));
        if (categoryId == null || !StringUtils.hasText(name) || price == null || stock == null) {
            throw new IllegalArgumentException("商品分类、名称、价格和库存不能为空");
        }
        String status = StringUtils.hasText(optional(input, "status")) ? optional(input, "status") : "ON_SALE";
        Long id = longValue(input.get("id"));
        if (id == null) {
            productMapper.insertProduct(categoryId, name, optional(input, "description"), price, stock, optional(input, "imageUrl"), status);
        } else {
            productMapper.updateProduct(id, categoryId, name, optional(input, "description"), price, stock, optional(input, "imageUrl"), status);
        }
    }

    public void deleteProduct(Long id) {
        if (productMapper.countProductReferences(id) > 0) {
            throw new IllegalArgumentException("商品已有购物车或订单记录，不能删除");
        }
        productMapper.deleteProduct(id);
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }

    private String optional(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return Long.valueOf(String.valueOf(value));
    }

    private Integer intValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return Integer.valueOf(String.valueOf(value));
    }

    private BigDecimal decimal(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return new BigDecimal(String.valueOf(value));
    }
}
