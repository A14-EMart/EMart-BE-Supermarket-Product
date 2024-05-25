package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);
    Product editProduct(UUID id, Product product);
    Product deleteProduct(UUID id);
    List<Product> getAllProduct();
    List<Product> searchProductByName(String name);
    List<Product> getAllProductBySupermarketId(UUID supermarketId);
    Product searchProductById(UUID id);
}
