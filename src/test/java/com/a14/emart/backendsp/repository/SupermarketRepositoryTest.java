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
    private SupermarketRepository supermarketRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Supermarket supermarket1;
    private Supermarket supermarket2;

    @BeforeEach
    public void setUp() {
        supermarket1 = new Supermarket("Supermarket One", "Description One", 1L, 10L, 50L, "http://image1.url");
        supermarket2 = new Supermarket("Supermarket Two", "Description Two", 2L, 20L, 100L, "http://image2.url");

        entityManager.persist(supermarket1);
        entityManager.persist(supermarket2);
    }

    @AfterEach
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        List<Supermarket> foundSupermarkets = supermarketRepository.findByNameContainingIgnoreCase("supermarket");

        assertEquals(2, foundSupermarkets.size());
        assertTrue(foundSupermarkets.contains(supermarket1));
        assertTrue(foundSupermarkets.contains(supermarket2));

        foundSupermarkets = supermarketRepository.findByNameContainingIgnoreCase("one");

        assertEquals(1, foundSupermarkets.size());
        assertTrue(foundSupermarkets.contains(supermarket1));

        foundSupermarkets = supermarketRepository.findByNameContainingIgnoreCase("two");

        assertEquals(1, foundSupermarkets.size());
        assertTrue(foundSupermarkets.contains(supermarket2));
    }
}
