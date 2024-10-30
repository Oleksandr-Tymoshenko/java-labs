package com.example.webstore.controller.api;

import com.example.webstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.webstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.webstore.dto.shoppingcart.ShoppingCartDto;
import com.example.webstore.service.CartItemService;
import com.example.webstore.service.ShoppingCartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartApiController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto addCartItem(@RequestBody @Valid CreateCartItemRequestDto requestDto,
                                       Authentication authentication) {
        return shoppingCartService.addCartItem(requestDto, authentication);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        return shoppingCartService.getShoppingCart(authentication);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto updateCartItem(@PathVariable @Positive Long id,
                                          @RequestBody UpdateCartItemRequestDto requestDto,
                                          Authentication authentication) {
        return cartItemService.updateCartItem(authentication, id, requestDto.quantity());
    }

    @DeleteMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto deleteCartItem(@PathVariable @Positive Long id,
                                          Authentication authentication) {
        return shoppingCartService.deleteCartItemById(authentication, id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(Authentication authentication) {
        shoppingCartService.clearShoppingCart(authentication);
    }
}
