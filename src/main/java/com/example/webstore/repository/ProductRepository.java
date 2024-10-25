package com.example.webstore.repository;

import com.example.webstore.model.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("FROM Product p ORDER BY p.createdAt DESC")
    List<Product> getRecentProducts(Pageable pageable);

    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findAllByPriceGreaterThanEqual(Double minPrice);
    List<Product> findAllByPriceLessThanEqual(Double maxPrice);
}
