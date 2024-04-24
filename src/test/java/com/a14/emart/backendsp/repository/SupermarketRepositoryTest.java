package com.a14.emart.backendsp.repository;

import com.a14.emart.backendsp.model.Supermarket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SupermarketRepositoryTest {

    @Autowired
    private SupermarketRepository supermarketRepository;

    @AfterEach
    public void tearDown() {
        supermarketRepository.deleteAll();
    }

    @Test
    public void testSaveSupermarket() {
        Supermarket supermarket = new Supermarket("Toko Uno", "Toko kelontong", "Pak Budi");

        supermarket = supermarketRepository.save(supermarket);

        assertThat(supermarket.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Supermarket supermarket = new Supermarket("Toko Uno", "Toko kelontong", "Pak Budi");
        supermarket = supermarketRepository.save(supermarket);

        Optional<Supermarket> retrievedSupermarket = supermarketRepository.findById(supermarket.getId());

        assertThat(retrievedSupermarket).isPresent();
        assertThat(retrievedSupermarket.get().getName()).isEqualTo("Toko Uno");
        assertThat(retrievedSupermarket.get().getDescription()).isEqualTo("Toko kelontong");
        assertThat(retrievedSupermarket.get().getPengelola()).isEqualTo("Pak Budi");
    }

    @Test
    public void testFindByNameContaining() {
        supermarketRepository.save(new Supermarket("Toko A", "Description A", "Pengelola A"));
        supermarketRepository.save(new Supermarket("Toko B", "Description B", "Pengelola B"));
        supermarketRepository.save(new Supermarket("Supermarket C", "Description C", "Pengelola C"));

        List<Supermarket> searchResults = supermarketRepository.findByNameContaining("Toko");

        assertThat(searchResults).hasSize(2);
        assertThat(searchResults).extracting(Supermarket::getName).contains("Toko A", "Toko B");
    }

    @Test
    public void testDeleteSupermarket() {
        Supermarket supermarket = new Supermarket("Toko Uno", "Toko kelontong", "Pak Budi");
        supermarket = supermarketRepository.save(supermarket);

        supermarketRepository.deleteById(supermarket.getId());

        assertThat(supermarketRepository.findById(supermarket.getId())).isEmpty();
    }
}
