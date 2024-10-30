package com.example.webstore.dto.order;

import com.example.webstore.model.Order;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequestDto(
        @NotBlank
        Order.OrderStatus status) {
}
