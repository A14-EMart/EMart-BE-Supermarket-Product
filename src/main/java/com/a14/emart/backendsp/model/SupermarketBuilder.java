package com.a14.emart.backendsp.model;
import java.util.UUID;

public class SupermarketBuilder {
    private UUID id;
    private String name;
    private String description;
    private Long pengelola;
    private Long totalReview;
    private Long totalScore;

    public SupermarketBuilder() {
        // Default constructor
    }

    public SupermarketBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public SupermarketBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SupermarketBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public SupermarketBuilder setPengelola(Long pengelola) {
        this.pengelola = pengelola;
        return this;
    }

    public SupermarketBuilder setTotalReview(Long totalReview) {
        this.totalReview = totalReview;
        return this;
    }

    public SupermarketBuilder setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    public Supermarket build() {
        Supermarket supermarket = new Supermarket();
        supermarket.setId(this.id);
        supermarket.setName(this.name);
        supermarket.setDescription(this.description);
        supermarket.setPengelola(this.pengelola);
        supermarket.setTotalReview(this.totalReview);
        supermarket.setTotalScore(this.totalScore);
        return supermarket;
    }
}
