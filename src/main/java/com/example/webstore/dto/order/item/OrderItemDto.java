package com.example.webstore.dto.order.item;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        Long productId,
        String productName,
        String productImage,
        Integer quantity,
        BigDecimal price
) {
}
