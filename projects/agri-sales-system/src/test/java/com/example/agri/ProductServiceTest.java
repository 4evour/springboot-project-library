package com.example.agri;

import com.example.agri.mapper.ProductMapper;
import com.example.agri.service.ProductService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    @Test
    void deleteProductRejectsReferencedProduct() {
        ProductMapper productMapper = mock(ProductMapper.class);
        when(productMapper.countProductReferences(1L)).thenReturn(1);
        ProductService productService = new ProductService(productMapper);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(1L));

        assertEquals("商品已有购物车或订单记录，不能删除", ex.getMessage());
    }

    @Test
    void deleteCategoryRejectsCategoryWithProducts() {
        ProductMapper productMapper = mock(ProductMapper.class);
        when(productMapper.countProductsByCategory(1L)).thenReturn(1);
        ProductService productService = new ProductService(productMapper);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> productService.deleteCategory(1L));

        assertEquals("分类下已有商品，不能删除", ex.getMessage());
    }

    @Test
    void deleteProductRemovesUnreferencedProduct() {
        ProductMapper productMapper = mock(ProductMapper.class);
        when(productMapper.countProductReferences(2L)).thenReturn(0);
        ProductService productService = new ProductService(productMapper);

        productService.deleteProduct(2L);

        verify(productMapper).deleteProduct(2L);
    }
}
