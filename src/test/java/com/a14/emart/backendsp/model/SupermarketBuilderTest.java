package com.a14.emart.backendsp.model;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.model.SupermarketBuilder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupermarketBuilderTest {

    @Test
    public void testBuilder() {
        UUID id = UUID.randomUUID();
        SupermarketBuilder builder = new SupermarketBuilder();
        Supermarket supermarket = builder.setId(id)
                .setName("Test Supermarket")
                .setDescription("A description")
                .setPengelola(1L)
                .setTotalReview(100L)
                .setTotalScore(500L)
                .build();

        assertNotNull(supermarket);
        assertEquals(id, supermarket.getId());
        assertEquals("Test Supermarket", supermarket.getName());
        assertEquals("A description", supermarket.getDescription());
        assertEquals(1L, supermarket.getPengelola());
        assertEquals(100L, supermarket.getTotalReview());
        assertEquals(500L, supermarket.getTotalScore());
    }

    @Test
    public void testBuilderWithNullId() {
        SupermarketBuilder builder = new SupermarketBuilder();
        Supermarket supermarket = builder.setName("Test Supermarket")
                .setDescription("A description")
                .setPengelola(1L)
                .setTotalReview(100L)
                .setTotalScore(500L)
                .build();

        assertNotNull(supermarket);
        assertEquals("Test Supermarket", supermarket.getName());
        assertEquals("A description", supermarket.getDescription());
        assertEquals(1L, supermarket.getPengelola());
        assertEquals(100L, supermarket.getTotalReview());
        assertEquals(500L, supermarket.getTotalScore());
    }
}
