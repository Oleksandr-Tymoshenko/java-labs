package com.example.webstore.service;

import com.example.webstore.dto.cartitem.CartItemDto;
import com.example.webstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.webstore.dto.shoppingcart.ShoppingCartDto;
import com.example.webstore.mapper.CartItemMapper;
import com.example.webstore.mapper.ShoppingCartMapper;
import com.example.webstore.model.CartItem;
import com.example.webstore.model.Product;
import com.example.webstore.model.ShoppingCart;
import com.example.webstore.model.User;
import com.example.webstore.repository.CartItemRepository;
import com.example.webstore.repository.ProductRepository;
import com.example.webstore.repository.ShoppingCartRepository;
import com.example.webstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemService cartItemService;
    private final UserService userService;

    @Transactional
    public ShoppingCartDto addCartItem(CreateCartItemRequestDto requestDto,
                                       Authentication authentication) {
        if (!productRepository.existsById(requestDto.productId())) {
            throw new EntityNotFoundException("Can't find book by id " + requestDto.productId());
        }
        User user = userService.findUserFromAuthentication(authentication);
        Optional<CartItem> cartItemByBookId = cartItemRepository
                .findCartItemByShoppingCartIdAndProductId(user.getId(), requestDto.productId());
        if (cartItemByBookId.isPresent()) {
            CartItem cartItem = cartItemByBookId.get();
            return cartItemService.updateCartItem(authentication, cartItem.getId(), requestDto.quantity());
        }

        CartItem cartItem = cartItemMapper.toCartItem(requestDto);
        Product bookFromDb = productRepository.getReferenceById(requestDto.productId());
        cartItem.setProduct(bookFromDb);
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        shoppingCart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = userService.findUserFromAuthentication(authentication);
        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("The shopping cart doesn’t exist"));
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartDto deleteCartItemById(Authentication authentication, Long itemId) {
        User user = userService.findUserFromAuthentication(authentication);
        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("The shopping cart doesn’t exist"));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(itemId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find item by id "
                        + itemId + " in your shopping cart"));
        shoppingCart.removeCartItem(cartItem);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    public void clearShoppingCart(Authentication authentication) {
        User user = userService.findUserFromAuthentication(authentication);
        shoppingCartRepository.deleteById(user.getId());
    }

    public double calculateTotalPrice(Set<CartItemDto> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.productPrice() * item.quantity())
                .sum();
    }

    private ShoppingCart getOrCreateShoppingCart(User user) {
        return shoppingCartRepository.findById(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(userRepository.getReferenceById(user.getId()));
                    return shoppingCartRepository.save(newShoppingCart);
                });
    }
}
