package com.a14.emart.backendsp.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
public class SupermarketTest {
    Supermarket supermarket;
    @BeforeEach
    void setUp(){
        this.supermarket = new Supermarket("Toko Uno", "Toko kelontong", "Pak Budi");
        this.supermarket.setId(UUID.randomUUID()); // Manually setting the UUID
    }

    @Test
    void testGetName() {
        assertEquals("Toko Uno", supermarket.getName(), "The name getter or constructor is not working correctly.");
    }

    @Test
    void testGetDescription() {
        assertEquals("Toko kelontong", supermarket.getDescription(), "The description getter or constructor is not working correctly.");
    }

    @Test
    void testGetPengelola() {
        assertEquals("Pak Budi", supermarket.getPengelola(), "The pengelola getter or constructor is not working correctly.");
    }
    @Test
    void testGetId(){
        UUID supermarketId = supermarket.getId();
        System.out.println(supermarketId);
        assertNotNull(supermarketId, "The ID should not be null after being explicitly set.");
    }



}
