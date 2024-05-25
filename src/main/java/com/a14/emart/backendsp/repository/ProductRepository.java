package com.a14.emart.backendsp.repository;

import com.a14.emart.backendsp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByNameContainingIgnoreCase(String name);
    List<Product> findAllBySupermarketId(UUID supermarketId);
}
