package com.a14.emart.backendsp.service;
import lombok.RequiredArgsConstructor;
import com.a14.emart.backendsp.repository.ProductRepository;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.Supermarket;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupermarketRepository supermarketRepository;
    private static String AUTH_BASE_URL = "";
    private WebClient webClient;

    public Supermarket createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        }
        productRepository.save(product);
        return supermarketRepository.save(product.getSupermarket());
    }

    public Product editProduct(String UUID, Product changeAttribute) {
        Product product = productRepository.findById(UUID).orElseThrow();
        product.setName(changeAttribute.getName());
        product.setPrice(changeAttribute.getPrice());
        product.setStock(changeAttribute.getStock());
        return productRepository.save(product);
    }

    public Product deleteProduct(String UUID) {
        Product product = productRepository.findById(UUID).orElseThrow();
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<Product> getAllProduct(Long supermarket) {
        List<Product> allProduct = supermarketRepository.getReferenceById(supermarket).getProducts();
        return allProduct;
    }

    public List<Product> searchProductByName(Supermarket supermarket, String name) {
        List<Product> searchProductByName = supermarket.getProducts();
        List<Product> productList = new ArrayList<>();
        for (Product product : searchProductByName) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                productList.add(product);
            }
        }
        return productList;
    }

    public Product searchProductById(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}