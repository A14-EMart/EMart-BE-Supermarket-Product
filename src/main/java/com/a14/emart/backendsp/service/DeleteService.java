package com.a14.emart.backendsp.service;

import java.util.UUID;
public interface DeleteService<T> {
    void deleteById(UUID entityId);
}