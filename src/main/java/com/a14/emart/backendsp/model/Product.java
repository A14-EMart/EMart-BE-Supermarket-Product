package com.a14.emart.backendsp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonIgnoreProperties(value = {"products", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    private Supermarket supermarket;

    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }
}
