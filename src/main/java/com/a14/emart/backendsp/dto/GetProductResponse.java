package com.a14.emart.backendsp.dto;

import com.a14.emart.backendsp.model.Product;
import java.util.UUID;

public class GetProductResponse {
    private UUID id;
    private String name;
    private Long price;
    private Integer stock;
    private String imageUrl;
    private UUID supermarketId;

    public GetProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.imageUrl = product.getImageUrl();
        this.supermarketId = product.getSupermarket().getId();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(UUID supermarketId) {
        this.supermarketId = supermarketId;
    }
}
