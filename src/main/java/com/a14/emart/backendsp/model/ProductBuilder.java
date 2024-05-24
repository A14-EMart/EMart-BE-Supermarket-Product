package com.a14.emart.backendsp.model;

public class ProductBuilder {
    private String name;
    private Long price;
    private Integer stock;
    private String imageUrl; // Add this line
    private Supermarket supermarket;

    public ProductBuilder setName(String name) {
        if ((name == null) || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (!stringValidation(name)) {
            throw new IllegalArgumentException("Name contains invalid characters");
        }

        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(Long price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.price = price;
        return this;
    }

    public ProductBuilder setStock(Integer stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.stock = stock;
        return this;
    }

    public ProductBuilder setImageUrl(String imageUrl) { // Add this method
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductBuilder setSupermarket(Supermarket supermarket) {
        if (supermarket == null) {
            throw new IllegalArgumentException("Supermarket cannot be null");
        }
        this.supermarket = supermarket;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setStock(this.stock);
        product.setImageUrl(this.imageUrl); // Add this line
        product.setSupermarket(this.supermarket);
        resetBuilder();
        return product;
    }

    private boolean stringValidation(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private void resetBuilder() {
        this.name = "";
        this.price = 0L;
        this.stock = 0;
        this.imageUrl = null; // Add this line
        this.supermarket = null;
    }
}
