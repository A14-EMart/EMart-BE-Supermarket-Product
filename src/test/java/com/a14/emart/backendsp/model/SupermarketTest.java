package com.a14.emart.backendsp.model;

import com.a14.emart.backendsp.model.Supermarket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupermarketTest {

    private Supermarket supermarket;

    @BeforeEach
    public void setUp() {
        supermarket = new Supermarket();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(supermarket);
    }

    @Test
    public void testParameterizedConstructor() {
        Supermarket supermarket = new Supermarket(
                "Test Supermarket",
                "A description",
                1L,
                100L,
                500L,
                "http://image.url"
        );

        assertEquals("Test Supermarket", supermarket.getName());
        assertEquals("A description", supermarket.getDescription());
        assertEquals(1L, supermarket.getPengelola());
        assertEquals(100L, supermarket.getTotalReview());
        assertEquals(500L, supermarket.getTotalScore());
        assertEquals("http://image.url", supermarket.getImageUrl());
    }

    @Test
    public void testSettersAndGetters() {
        UUID id = UUID.randomUUID();
        supermarket.setId(id);
        supermarket.setName("Test Supermarket");
        supermarket.setDescription("A description");
        supermarket.setPengelola(1L);
        supermarket.setTotalReview(100L);
        supermarket.setTotalScore(500L);
        supermarket.setImageUrl("http://image.url");

        assertEquals(id, supermarket.getId());
        assertEquals("Test Supermarket", supermarket.getName());
        assertEquals("A description", supermarket.getDescription());
        assertEquals(1L, supermarket.getPengelola());
        assertEquals(100L, supermarket.getTotalReview());
        assertEquals(500L, supermarket.getTotalScore());
        assertEquals("http://image.url", supermarket.getImageUrl());
    }
}
