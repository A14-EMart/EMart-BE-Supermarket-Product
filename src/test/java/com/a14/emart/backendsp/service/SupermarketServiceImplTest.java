package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import com.a14.emart.backendsp.service.CreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SupermarketServiceImplTest {

    @Autowired
    private CreateService<Supermarket> createService;

    @Autowired
    private ReadService<Supermarket> readService;
    @Autowired
    private SupermarketRepository supermarketRepository;

    @BeforeEach
    void setup() {
        // Clear the repository to ensure it's empty
        supermarketRepository.deleteAll();

        // Pre-populate the database
        supermarketRepository.save(new Supermarket("Supermarket 1", "Description 1", "Pengelola 1"));
        supermarketRepository.save(new Supermarket("Supermarket 2", "Description 2", "Pengelola 2"));
        supermarketRepository.save(new Supermarket("Supermarket 3", "Description 3", "Pengelola 3"));
    }

    @Test
    @Transactional
    public void testCreateSupermarket() {
        // Given
        Supermarket newSupermarket = new Supermarket("Test Supermarket", "A supermarket providing all essentials.", "John Doe");

        // When
        Supermarket savedSupermarket = createService.create(newSupermarket);


        assertNotNull(savedSupermarket.getId(), "Supermarket should have an ID after being saved");
        assertEquals(newSupermarket.getName(), savedSupermarket.getName(), "Names should match");
        assertEquals(newSupermarket.getDescription(), savedSupermarket.getDescription(), "Descriptions should match");
        assertEquals(newSupermarket.getPengelola(), savedSupermarket.getPengelola(), "Pengelola should match");

        // Optionally, verify the supermarket is saved in the database
        Supermarket retrievedSupermarket = supermarketRepository.findById(savedSupermarket.getId()).orElse(null);
        assertNotNull(retrievedSupermarket, "Supermarket should be retrievable from the database");
        assertEquals("Test Supermarket", retrievedSupermarket.getName(), "Name should be 'Test Supermarket'");
    }

    @Test
    @Transactional
    public void testFindAllSupermarkets() {
        // When
        List<Supermarket> supermarkets = readService.findAll();

        // Then
        assertEquals(3, supermarkets.size(), "Should find all three supermarkets");
        assertFalse(supermarkets.isEmpty(), "The list of supermarkets should not be empty");
    }

    @Test
    @Transactional
    public void testFindById() {
        Supermarket newSupermarket = new Supermarket("Test Supermarket", "A supermarket providing all essentials.", "John Doe");
        Supermarket savedSupermarket = createService.create(newSupermarket);
        UUID supermarketId = savedSupermarket.getId();  // Save the ID for test usage
        // When
        Supermarket foundSupermarket = readService.findById(supermarketId);

        // Then
        assertEquals(supermarketId, foundSupermarket.getId(), "The ID of the found supermarket should match the saved supermarket's ID");

        // Test fetching a non-existing supermarket
        Supermarket notFoundSupermarket = readService.findById(UUID.randomUUID());
        assertNull(notFoundSupermarket, "Should return null for a non-existing supermarket ID");
    }
}
