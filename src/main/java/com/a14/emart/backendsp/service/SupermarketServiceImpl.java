package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class SupermarketServiceImpl implements CreateService<Supermarket>, ReadService<Supermarket>, UpdateService<Supermarket>, DeleteService<Supermarket>{

    @Autowired
    SupermarketRepository supermarketRepository;
    @Override
    public Supermarket create(Supermarket supermarket){
        return supermarketRepository.save(supermarket);

    }
    @Override
    public List<Supermarket> findAll(){
        return supermarketRepository.findAll();
    }
    @Override
    public Supermarket findById(UUID id){
        return supermarketRepository.findById(id).orElse(null);
    }

    @Override
    public List<Supermarket> findByMatch(String param){
        return null;
    }

    @Override
    public void update(UUID id, Supermarket newSupermarket){

    }
    @Override
    public void deleteById(UUID id){

    }

}
