package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.dto.CreateProductRequest;
import com.a14.emart.backendsp.dto.DeleteProductRequest;
import com.a14.emart.backendsp.dto.ModifyProductResponse;
import com.a14.emart.backendsp.model.ProductBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-product")
    public ResponseEntity<List<Product>> allProduct() {
        List<Product> allProduct = productService.getAllProduct();
        return ResponseEntity.ok(allProduct);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Product>> queryProduct(@PathVariable("query") String query) {
        List<Product> queryProduct = productService.searchProductByName(query);
        return ResponseEntity.ok(queryProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest product) {
        Product savedProduct = productService.createProduct(new ProductBuilder()
                .setName(product.name)
                .setStock(product.stock)
                .setPrice(product.price)
                .build());
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Product> editProduct(@RequestBody ModifyProductResponse product) {
        Product savedProduct = productService.editProduct(product.UUID,
                new ProductBuilder()
                        .setName(product.name)
                        .setPrice(product.price)
                        .setStock(product.stock)
                        .build());
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Product> deleteProduct(@RequestBody DeleteProductRequest request) {
        Product savedProduct = productService.deleteProduct(request.UUID);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Product> searchProductById(@PathVariable("id") String id) {
        Product queryProduct = productService.searchProductById(id);
        return ResponseEntity.ok(queryProduct);
    }
}