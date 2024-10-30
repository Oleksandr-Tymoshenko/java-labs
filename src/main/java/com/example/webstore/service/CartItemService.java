package com.example.webstore.service;

import com.example.webstore.dto.shoppingcart.ShoppingCartDto;
import com.example.webstore.mapper.ShoppingCartMapper;
import com.example.webstore.model.CartItem;
import com.example.webstore.model.User;
import com.example.webstore.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Transactional
    public ShoppingCartDto updateCartItem(Authentication authentication, Long itemId,
                                          Integer quantity) {
        User user = userService.findUserFromAuthentication(authentication);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(itemId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find item by id "
                        + itemId + " in your shopping cart"));
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toShoppingCartDto(cartItem.getShoppingCart());
    }
}
