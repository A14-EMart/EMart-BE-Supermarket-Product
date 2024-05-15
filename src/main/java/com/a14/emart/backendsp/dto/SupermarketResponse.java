package com.a14.emart.backendsp.dto;

import com.a14.emart.backendsp.model.Supermarket;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
public class SupermarketResponse {

    private UUID id;
    private String name;
    private String description;
    private Long pengelola;
    private double rating;

    public SupermarketResponse(Supermarket supermarket) {
        this.id = supermarket.getId();
        this.name = supermarket.getName();
        this.description = supermarket.getDescription();
        this.pengelola = supermarket.getPengelola();
        this.rating = supermarket.getTotalReview() != 0 ? Math.round(((double) supermarket.getTotalScore() / supermarket.getTotalReview()) * 10.0) / 10.0 : 0.0;
    }

}
