package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.dto.EditSupermarketRequest;
import com.a14.emart.backendsp.model.Product;
import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.ProductRepository;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupermarketService {
    private final SupermarketRepository supermarketRepository;
    private final ProductRepository productRepository;

    public Supermarket addManager(Long supermarketId, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();

        if (!supermarket.getManagers().contains(managerEmail)) {
            supermarket.getManagers().add(managerEmail);
            supermarketRepository.save(supermarket);
        }

        return supermarket;
    }

    public Supermarket removeManager(Long supermarketId, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();

        if (!supermarket.getManagers().contains(managerEmail)) {
            throw new IllegalArgumentException();
        }

        supermarket.getManagers().remove(managerEmail);
        supermarketRepository.save(supermarket);
        return supermarket;
    }

    public Supermarket getSupermarket(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return supermarketRepository.findById(id).orElseThrow();
    }

    public List<Supermarket> getAllSupermarkets() {
        return supermarketRepository.findAll();
    }

    public Supermarket createSupermarket(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = new Supermarket();
        supermarket.setName(name);
        supermarket.setManagers(new ArrayList<>());
        supermarket.setProducts(new ArrayList<>());

        return supermarketRepository.save(supermarket);
    }

    public void deleteSupermarket(Long id) {
        Supermarket supermarket = getSupermarket(id);

        supermarketRepository.delete(supermarket);
    }

    public Supermarket editSupermarket(Long id, EditSupermarketRequest newSupermarket) {
        if (newSupermarket == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = getSupermarket(id);

        supermarket.setName(newSupermarket.getName());

        return supermarketRepository.save(supermarket);
    }
}
