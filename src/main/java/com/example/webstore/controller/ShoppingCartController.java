package com.example.webstore.controller;

import com.example.webstore.dto.cartitem.CartItemDto;
import com.example.webstore.dto.order.CreateOrderRequestDto;
import com.example.webstore.model.User;
import com.example.webstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public String showCart(Model model, Authentication authentication, @AuthenticationPrincipal User user) {
        Set<CartItemDto> cartItems;
        try {
            cartItems = shoppingCartService.getShoppingCart(authentication).cartItems();
        } catch (EntityNotFoundException e) {
            cartItems = new HashSet<>();
        }

        double totalPrice = shoppingCartService.calculateTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto();
        if (user.getAddress() != null) {
            createOrderRequestDto.setShippingAddress(user.getAddress());
        }
        if (user.getPhone() != null) {
            createOrderRequestDto.setPhone(user.getPhone());
        }
        model.addAttribute(createOrderRequestDto);
        return "shopping-cart";
    }
}
