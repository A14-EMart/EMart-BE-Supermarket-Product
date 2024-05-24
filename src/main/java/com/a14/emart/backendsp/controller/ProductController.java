package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.dto.CreateProductRequest;
import com.a14.emart.backendsp.dto.ModifyProductResponse;
import com.a14.emart.backendsp.dto.SupermarketResponse;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.ProductBuilder;
import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.service.CloudinaryService;
import com.a14.emart.backendsp.service.JwtService;
import com.a14.emart.backendsp.service.ProductService;
import com.a14.emart.backendsp.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.a14.emart.backendsp.controller.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ProductController {


    @Autowired
    private final ProductService productService;

    @Autowired
    private ReadService<Supermarket> readService;

    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;

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
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("stock") Integer stock,
            @RequestParam("supermarketId") UUID supermarketId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer ", "");
            String role = jwtService.extractRole(tokenWithoutBearer);
            System.out.println(role);

            if (!role.equalsIgnoreCase("manager")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(false, null, "You have no access."));
            }


            // Fetch the supermarket entity
            Supermarket target = readService.findById(supermarketId);
            if(target == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, null, "Supermarket not found."));
            }

            if(!target.getPengelola().equals(jwtService.extractUserId(tokenWithoutBearer))){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(false, null, "You have no access."));
            }

            String imageUrl = cloudinaryService.uploadFile(file);

            Product product = new ProductBuilder()
                    .setName(name)
                    .setStock(stock)
                    .setPrice(price)
                    .setSupermarket(target)
                    .setImageUrl(imageUrl)
                    .build();
            Product savedProduct = productService.createProduct(product);
            if(savedProduct == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, null, "Failed to add product. Input may be invalid."));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, savedProduct, "Product added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

