package com.a14.emart.backendsp.repository;

import com.a14.emart.backendsp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByNameIsContainingIgnoreCase(String name);
}

