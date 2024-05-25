package com.a14.emart.backendsp.repository;

import com.a14.emart.backendsp.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, UUID> {
    List<Supermarket> findByNameContainingIgnoreCase(String name);

}