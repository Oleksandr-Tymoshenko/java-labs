package com.example.webstore.dto.cartitem;

public record CartItemDto(
        Long id,
        String productImage,
        String productName,
        double productPrice,
        Integer quantity) {
}
