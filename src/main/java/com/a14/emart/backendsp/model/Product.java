package com.a14.emart.backendsp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "supermarket_id", nullable = false)
    @JsonBackReference
    private Supermarket supermarket;

    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }
}
