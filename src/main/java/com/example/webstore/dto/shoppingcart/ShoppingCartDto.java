package com.example.webstore.dto.shoppingcart;

import com.example.webstore.dto.cartitem.CartItemDto;
import java.util.Set;

public record ShoppingCartDto(Long id, Long userId, Set<CartItemDto> cartItems) {
}
