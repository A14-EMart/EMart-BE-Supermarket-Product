package com.a14.emart.backendsp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "supermarket")
@Getter
@Setter
public class Supermarket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    private Long pengelola;

    @Column(nullable = false)
    private Long totalReview;

    @Column(nullable = false)
    private Long totalScore;


    public Supermarket() {
        // Default constructor
    }

    public Supermarket(String name, String description, Long pengelola, Long totalReview, Long totalScore) {
        this.name = name;
        this.description = description;
        this.pengelola = pengelola;
        this.totalReview = totalReview;
        this.totalScore = totalScore;
    }


}