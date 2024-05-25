package com.a14.emart.backendsp.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ModifyProductResponse {
    public String UUID;
    public String name;
    public Long price;
    public Integer stock;
}