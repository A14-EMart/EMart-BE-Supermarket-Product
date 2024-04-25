package com.a14.emart.backendsp.repository;

import com.a14.emart.backendsp.model.Supermarket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupermarketRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SupermarketRepository supermarketRepository;

    @BeforeEach
    void setUp() {
        Supermarket market1 = new Supermarket("Fresh Market", "A fresh food market offering a variety of organic foods.", "John Doe");
        entityManager.persist(market1);

        Supermarket market2 = new Supermarket("Green Fresh Market", "Specializes in green, organic produce and eco-friendly products.", "Jane Doe");
        entityManager.persist(market2);

        Supermarket market3 = new Supermarket("Ultra Mart", "Provides all your daily needs from groceries to household items.", "Alice Johnson");
        entityManager.persist(market3);

        entityManager.flush();  // Ensures all entities are persisted before each test
    }
    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void testFindByNameContainingReturnsCorrectResults() {
        List<Supermarket> results = supermarketRepository.findByNameContainingIgnoreCase("Fresh");
        assertEquals(2, results.size());
    }

    @Test
    void testFindByNameContainingReturnsEmptyIfNoMatch() {
        List<Supermarket> results = supermarketRepository.findByNameContainingIgnoreCase("Bazaar");
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByNameContainingIsCaseInsensitive() {
        List<Supermarket> results = supermarketRepository.findByNameContainingIgnoreCase("fresh");
        assertEquals(2, results.size());
    }

    @Test
    void testFindByNameContainingHandlesPartialMatches() {
        List<Supermarket> results = supermarketRepository.findByNameContainingIgnoreCase("Market");
        assertEquals(2, results.size()); // Should find all entries with "Market" in their name
    }

}
