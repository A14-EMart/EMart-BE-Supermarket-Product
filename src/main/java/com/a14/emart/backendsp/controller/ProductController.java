package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.dto.CreateProductRequest;
import com.a14.emart.backendsp.dto.ModifyProductResponse;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.ProductBuilder;
import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.service.ProductService;
import com.a14.emart.backendsp.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
public class ProductController {


    @Autowired
    private final ProductService productService;

    @Autowired
    private ReadService<Supermarket> readService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> allProduct() {
        List<Product> allProduct = productService.getAllProduct();
        return ResponseEntity.ok(allProduct);
    }

    @GetMapping("/supermarket/{supermarketId}")
    public ResponseEntity<List<Product>> getProductsBySupermarketId(@PathVariable UUID supermarketId) {
        List<Product> products = productService.getAllProductBySupermarketId(supermarketId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Product>> queryProduct(@PathVariable("query") String query) {
        List<Product> queryProduct = productService.searchProductByName(query);
        return ResponseEntity.ok(queryProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest productRequest) {
        Supermarket target = readService.findById(productRequest.getSupermarketId());
        Product product = new ProductBuilder()
                .setName(productRequest.getName())
                .setStock(productRequest.getStock())
                .setPrice(productRequest.getPrice())
                .setSupermarket(target)
                .build();
        Product savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable UUID id,
                                               @RequestBody ModifyProductResponse productRequest) {
        Product existingProduct = productService.searchProductById(id);
        Product updatedProduct = new ProductBuilder()
                .setName(productRequest.getName())
                .setPrice(productRequest.getPrice())
                .setStock(productRequest.getStock())
                .setSupermarket(existingProduct.getSupermarket())
                .build();
        updatedProduct.setId(id);
        Product savedProduct = productService.editProduct(id, updatedProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Product> searchProductById(@PathVariable UUID id) {
        Product queryProduct = productService.searchProductById(id);
        return ResponseEntity.ok(queryProduct);
    }

    @GetMapping("/findByMultipleId/{ids}")
    public ResponseEntity<List<Product>> searchProductByMultipleId(@PathVariable String ids) {
        String[] idArray = ids.split(",");
        List<Product> products = new ArrayList<>();
        for (String id : idArray) {
            Product queryProduct = productService.searchProductById(UUID.fromString(id.trim()));
            products.add(queryProduct);
        }
        return ResponseEntity.ok(products);
    }
}

