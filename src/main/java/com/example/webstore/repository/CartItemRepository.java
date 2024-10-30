package com.example.webstore.repository;

import com.example.webstore.model.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);

    @EntityGraph(attributePaths = {"shoppingCart", "product"})
    Optional<CartItem> findCartItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    @EntityGraph(attributePaths = {"shoppingCart", "product"})
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);
}
