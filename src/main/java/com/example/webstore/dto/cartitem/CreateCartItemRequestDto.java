package com.example.webstore.dto.cartitem;

import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @Positive
        Long productId,

        @Positive
        Integer quantity
) {
}
