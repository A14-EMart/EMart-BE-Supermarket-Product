package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.a14.emart.backendsp.service.CreateService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupermarketServiceImplTest {

    private CreateService<Supermarket> createSupermarketService;
    private SupermarketRepository supermarketRepository;

    @Test
    @Transactional
    public void testCreateSupermarket() {
        // Given
        Supermarket newSupermarket = new Supermarket("Test Supermarket", "A supermarket providing all essentials.", "John Doe");

        // When
        Supermarket savedSupermarket = createSupermarketService.create(newSupermarket);

        // Then
        assertNotNull(savedSupermarket.getId(), "Supermarket should have an ID after being saved");
        assertEquals(newSupermarket.getName(), savedSupermarket.getName(), "Names should match");
        assertEquals(newSupermarket.getDescription(), savedSupermarket.getDescription(), "Descriptions should match");
        assertEquals(newSupermarket.getPengelola(), savedSupermarket.getPengelola(), "Pengelola should match");

        // Optionally, verify the supermarket is saved in the database
        Supermarket retrievedSupermarket = supermarketRepository.findById(savedSupermarket.getId()).orElse(null);
        assertNotNull(retrievedSupermarket, "Supermarket should be retrievable from the database");
        assertEquals("Test Supermarket", retrievedSupermarket.getName(), "Name should be 'Test Supermarket'");
        assertEquals("A supermarket providing all essentials.", retrievedSupermarket.getDescription(), "Description should match");
        assertEquals("John Doe", retrievedSupermarket.getPengelola(), "Pengelola should match");
    }


}
