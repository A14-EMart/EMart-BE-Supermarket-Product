package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.Supermarket;
import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public Product editProduct(String UUID, Product product);
    public Product deleteProduct(String UUID);
    public List<Product> getAllProduct();
    public List<Product> searchProductByName(Supermarket supermarket, String name);
    public Product searchProductById(String id);
}
