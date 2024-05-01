package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Product;
import java.util.List;

public interface ProductService {
    public Product createProduct(String name, Long price, Integer stock);
    public Product editProduct(String UUID, Product changeAttribute);
    public Product deleteProduct(String UUID);
    public List<Product> getAllProduct();
    public List<Product> searchProduct(String name);
}
