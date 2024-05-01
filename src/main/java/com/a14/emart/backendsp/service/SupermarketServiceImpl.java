package com.a14.emart.backendsp.service;

import com.a14.emart.backendsp.model.Supermarket;

import java.util.List;
import java.util.UUID;
public class SupermarketServiceImpl implements CreateService<Supermarket>, ReadService<Supermarket>, UpdateService<Supermarket>, DeleteService<Supermarket>{
    @Override
    public Supermarket create(Supermarket supermarket){
        return null;
    }
    @Override
    public List<Supermarket> findAll(){
        return null;
    }
    @Override
    public Supermarket findById(UUID id){
        return null;
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
