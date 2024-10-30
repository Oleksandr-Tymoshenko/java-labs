package com.example.webstore.repository;

import com.example.webstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product", "orderItems.order"})
    List<Order> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product", "orderItems.order"})
    Optional<Order> findById(Long orderId);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product", "orderItems.order"})
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
