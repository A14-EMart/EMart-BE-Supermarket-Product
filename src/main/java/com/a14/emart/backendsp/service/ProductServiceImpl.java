package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product editProduct(Product editedProduct) {
        productRepository.edit(editedProduct);
        return editedProduct;
    }

    @Override
    public Product deleteProduct(String productId) {
        return productRepository.delete(productId);
    }

}