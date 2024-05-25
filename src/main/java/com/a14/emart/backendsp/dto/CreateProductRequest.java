package com.a14.emart.backendsp.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private Long price;
    private Integer stock;
    private UUID supermarketId;
}