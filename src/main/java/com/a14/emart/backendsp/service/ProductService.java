package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Product;
import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public Product editProduct(String UUID, Product product);
    public Product deleteProduct(String UUID);
    public List<Product> getAllProduct();
    public List<Product> searchProductByName(String name);
    public Product searchProductById(String id);
}
