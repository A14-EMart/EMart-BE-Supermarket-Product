package com.a14.emart.backendsp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "supermarket")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Supermarket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Lob
    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Column(nullable = false)
    private Long pengelola;

    @Column(nullable = false)
    private Long totalReview;

    @Column(nullable = false)
    private Long totalScore;

    @Column(nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "supermarket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"supermarket", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<Product> products;

    public Supermarket() {
        // Default constructor
    }

    public Supermarket(String name, String description, Long pengelola, Long totalReview, Long totalScore, String imageUrl) {
        this.name = name;
        this.description = description;
        this.pengelola = pengelola;
        this.totalReview = totalReview;
        this.totalScore = totalScore;
        this.imageUrl = imageUrl;
    }
}
