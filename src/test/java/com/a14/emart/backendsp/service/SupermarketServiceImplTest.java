package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private UpdateService<Supermarket> updateService;

    @Autowired
    private DeleteService<Supermarket> deleteService;

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

    @Test
    @Transactional
    public void testFindByMatch() {
        createService.create(new Supermarket("Fresh Foods Market", "Provides fresh fruits and vegetables.", "Alice"));
        createService.create(new Supermarket("Quick Stop Market", "Convenient stop for all your quick shopping needs.", "Bob"));
        createService.create(new Supermarket("Budget Bites Market", "Affordable groceries for everyone.", "Charlie"));
        // When searching for a common term
        List<Supermarket> results = readService.findByMatch("Market");

        // Then
        assertEquals(6, results.size(), "Should match all six markets");

        // When searching for a specific term
        results = readService.findByMatch("Fresh");
        assertEquals(1, results.size(), "Should find one supermarket with 'Fresh' in its name");
        assertEquals("Fresh Foods Market", results.getFirst().getName(), "The supermarket name should match 'Fresh Foods Market'");

        // When searching for a non-existing term
        results = readService.findByMatch("Zebra");
        assertTrue(results.isEmpty(), "Should return an empty list for a non-matching search term");
    }

    @Test
    @Transactional
    public void testUpdateSupermarket_Successful() {
        Supermarket supermarket = new Supermarket("Initial Supermarket", "Initial Description", "Initial Pengelola");
        Supermarket savedSupermarket = createService.create(supermarket);
        UUID supermarketId = savedSupermarket.getId();
        // Given
        Supermarket updatedSupermarket = new Supermarket("Updated Supermarket", "Updated Description", "Updated Pengelola");

        // When
        Supermarket result = updateService.update(supermarketId, updatedSupermarket);

        // Then
        assertNotNull(result, "Updated supermarket should not be null");
        assertEquals(supermarketId, result.getId(), "ID should remain the same");
        assertEquals("Updated Supermarket", result.getName(), "Name should be updated");
        assertEquals("Updated Description", result.getDescription(), "Description should be updated");
        assertEquals("Updated Pengelola", result.getPengelola(), "Pengelola should be updated");
    }

    @Test
    @Transactional
    public void testUpdateSupermarket_NonExistentID() {
        // Given
        Supermarket updatedSupermarket = new Supermarket("Updated Supermarket", "Updated Description", "Updated Pengelola");
        UUID nonExistentId = UUID.randomUUID();

        // When
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            updateService.update(nonExistentId, updatedSupermarket);
        });

        // Then
        String expectedMessage = "Supermarket not found with id " + nonExistentId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message should contain the correct ID");
    }

    @Test
    @Transactional
    public void testDeleteById_Successful() {
        Supermarket supermarket = new Supermarket("Initial Supermarket", "Initial Description", "Initial Pengelola");
        Supermarket savedSupermarket = createService.create(supermarket);
        UUID supermarketId = savedSupermarket.getId();
        // Given
        assertTrue(supermarketRepository.existsById(supermarketId), "Supermarket should exist before deletion");

        // When
        boolean result = deleteService.deleteById(supermarketId);

        // Then
        assertTrue(result, "Deletion should return true indicating success");
        assertFalse(supermarketRepository.existsById(supermarketId), "Supermarket should no longer exist after deletion");
    }

    @Test
    @Transactional
    public void testDeleteById_NonExistentID() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When
        boolean result = deleteService.deleteById(nonExistentId);

        // Then
        assertFalse(result, "Deletion should return false indicating the supermarket did not exist");
    }


}
