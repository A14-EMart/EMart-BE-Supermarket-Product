package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.dto.CreateProductRequest;
import com.a14.emart.backendsp.dto.DeleteProductRequest;
import com.a14.emart.backendsp.dto.ModifyProductResponse;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.ProductBuilder;
import com.a14.emart.backendsp.service.JwtService;
import com.a14.emart.backendsp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    @Value("${spring.route.gateway_url}")
    private String GATEWAY_URL;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupermarketService supermarketService;

    private final JwtService jwtService;

    @GetMapping("/all-product/{query}")
    public ResponseEntity<List<Product>> allProduct(@PathVariable("query") Long supermarketId) {
        List<Product> allProduct = productService.getAllProduct(supermarketId);
        return ResponseEntity.ok(allProduct);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Product>> queryProduct(@PathVariable("query") String query) {
        List<Product> queryProduct = productService.searchProductByName(query);
        return ResponseEntity.ok(queryProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestHeader (value = "Authorization") String id,
                                                 @RequestBody CreateProductRequest product) throws IllegalAccessException {

        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("manager")
                && !jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }
        Supermarket target = supermarketService.getSupermarket(product.supermarketId);
        Product productnya = new ProductBuilder()
                .setName(product.name)
                .setStock(product.stock)
                .setPrice(product.price)
                .setSupermarket(target)
                .build();
        Supermarket savedProduct = productService.createProduct(productnya);
        return new ResponseEntity<>(productnya, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Product> editProduct(@RequestHeader (value = "Authorization") String id,
                                               @RequestBody ModifyProductResponse product) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("manager")
                && !jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        Product savedProduct = productService.editProduct(product.UUID,
                new ProductBuilder()
                        .setName(product.name)
                        .setPrice(product.price)
                        .setStock(product.stock)
                        .build());
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Product> deleteProduct(@RequestHeader (value = "Authorization") String id,
                                                 @RequestBody DeleteProductRequest request) throws IllegalAccessException{
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("manager")
                && !jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        Product savedProduct = productService.deleteProduct(request.UUID);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Product> searchProductById(@PathVariable("id") String id) {
        Product queryProduct = productService.searchProductById(id);
        return ResponseEntity.ok(queryProduct);
    }

    @GetMapping("/findByMultipleId/{id}")
    public ResponseEntity<List<Product>> searchProductByMultipleId(@PathVariable("id") String id) throws IllegalAccessException {
        String[] ids = id.split(",");
        List<Product> products = new ArrayList<>();

        for (String productId : ids) {
            Product queryProduct = productService.searchProductById(productId.trim()); // Trim to remove any leading/trailing spaces
            if (queryProduct != null) {
                products.add(queryProduct);
            }

            else {
                throw new IllegalAccessException("Terdapat item yang tidak valid!");
            }
        }
        return ResponseEntity.ok(products);
    }
}