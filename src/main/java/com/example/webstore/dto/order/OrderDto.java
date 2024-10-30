package com.example.webstore.dto.order;

import com.example.webstore.dto.order.item.OrderItemDto;
import com.example.webstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        Long id,
        Long userId,
        Set<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        String shippingAddress,
        String phone,
        BigDecimal total,
        Order.OrderStatus status
) {
}
