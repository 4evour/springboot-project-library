package com.example.agri.service;

import com.example.agri.mapper.CartMapper;
import com.example.agri.mapper.OrderMapper;
import com.example.agri.mapper.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CartOrderService {
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    public CartOrderService(CartMapper cartMapper, OrderMapper orderMapper, ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    public List<Map<String, Object>> listCart(Long userId) {
        return cartMapper.listByUser(userId);
    }

    public void addCart(Long userId, Long productId, Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("商品和数量不能为空");
        }
        cartMapper.add(userId, productId, quantity);
    }

    public void updateCart(Long userId, Long id, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("数量必须大于 0");
        }
        cartMapper.updateQuantity(id, userId, quantity);
    }

    public void deleteCart(Long userId, Long id) {
        cartMapper.delete(id, userId);
    }

    @Transactional
    public Long checkout(Long userId, Map<String, Object> input) {
        String receiverName = text(input, "receiverName");
        String receiverPhone = text(input, "receiverPhone");
        String address = text(input, "address");
        if (!StringUtils.hasText(receiverName) || !StringUtils.hasText(receiverPhone) || !StringUtils.hasText(address)) {
            throw new IllegalArgumentException("收货信息不能为空");
        }
        List<Map<String, Object>> cart = cartMapper.listByUser(userId);
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("购物车为空");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String, Object> item : cart) {
            BigDecimal price = (BigDecimal) item.get("price");
            Integer quantity = ((Number) item.get("quantity")).intValue();
            total = total.add(price.multiply(new BigDecimal(quantity)));
        }
        String orderNo = "A" + UUID.randomUUID().toString().replace("-", "").substring(0, 18);
        orderMapper.insertOrder(userId, orderNo, receiverName, receiverPhone, address, total);
        Long orderId = orderMapper.lastInsertId();
        for (Map<String, Object> item : cart) {
            Long productId = ((Number) item.get("productId")).longValue();
            Integer quantity = ((Number) item.get("quantity")).intValue();
            if (productMapper.decreaseStock(productId, quantity) == 0) {
                throw new IllegalArgumentException("商品库存不足：" + item.get("productName"));
            }
            BigDecimal price = (BigDecimal) item.get("price");
            BigDecimal subtotal = price.multiply(new BigDecimal(quantity));
            orderMapper.insertItem(orderId, productId, String.valueOf(item.get("productName")), price, quantity, subtotal);
        }
        cartMapper.clearByUser(userId);
        return orderId;
    }

    public List<Map<String, Object>> listMyOrders(Long userId) {
        List<Map<String, Object>> orders = orderMapper.listByUser(userId);
        attachItems(orders);
        return orders;
    }

    public List<Map<String, Object>> listAllOrders() {
        List<Map<String, Object>> orders = orderMapper.listAll();
        attachItems(orders);
        return orders;
    }

    public void updateOrderStatus(Long id, String status) {
        if (!"PENDING".equals(status) && !"SHIPPED".equals(status) && !"FINISHED".equals(status) && !"CANCELLED".equals(status)) {
            throw new IllegalArgumentException("订单状态不正确");
        }
        orderMapper.updateStatus(id, status);
    }

    private void attachItems(List<Map<String, Object>> orders) {
        for (Map<String, Object> order : orders) {
            Long id = ((Number) order.get("id")).longValue();
            order.put("items", orderMapper.listItems(id));
        }
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }
}
