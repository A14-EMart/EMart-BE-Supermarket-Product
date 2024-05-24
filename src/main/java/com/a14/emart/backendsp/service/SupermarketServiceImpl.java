package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.config.RabbitMQConfig;
import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupermarketServiceImpl implements CreateService<Supermarket>, ReadService<Supermarket>, UpdateService<Supermarket>, DeleteService<Supermarket> {

    @Autowired
    private SupermarketRepository supermarketRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Supermarket create(Supermarket supermarket) {
        // Save supermarket
        Supermarket createdSupermarket = supermarketRepository.save(supermarket);

        // Send message to auth microservice
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, supermarket.getPengelola());

        return createdSupermarket;
    }

    @Override
    public List<Supermarket> findAll() {
        return supermarketRepository.findAll();
    }

    @Override
    public Supermarket findById(UUID id) {
        return supermarketRepository.findById(id).orElse(null);
    }

    @Override
    public List<Supermarket> findByMatch(String param) {
        return supermarketRepository.findByNameContainingIgnoreCase(param);
    }

    @Override
    public Supermarket update(UUID id, Supermarket newSupermarket) {
        Supermarket existingSupermarket = supermarketRepository.findById(id).orElse(null);
        if (existingSupermarket == null) {
            throw new EntityNotFoundException("Supermarket not found with id " + id);
        }

        existingSupermarket.setName(newSupermarket.getName());
        existingSupermarket.setDescription(newSupermarket.getDescription());
        existingSupermarket.setPengelola(newSupermarket.getPengelola());
        existingSupermarket.setTotalReview(newSupermarket.getTotalReview());
        existingSupermarket.setTotalScore(newSupermarket.getTotalScore());

        return supermarketRepository.save(existingSupermarket);
    }

    @Override
    public boolean deleteById(UUID id) {
        if (!supermarketRepository.existsById(id)) {
            return false;
        }
        supermarketRepository.deleteById(id);
        return true;
    }
}
