package com.a14.emart.backendsp.service;

import java.util.List;
import java.util.UUID;
public interface ReadService<T> {
    List<T> findAll();
    T findById(UUID id);
    List<T> findByMatch(String name);
}