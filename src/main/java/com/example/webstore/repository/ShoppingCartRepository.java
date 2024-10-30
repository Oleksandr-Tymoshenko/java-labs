package com.example.webstore.repository;

import com.example.webstore.model.ShoppingCart;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.product", "user"})
    Optional<ShoppingCart> findById(@NonNull Long id);
}
