package com.a14.emart.backendsp.service;
import java.util.UUID;
public interface UpdateService<T> {
    void update(UUID entityId, T entity);
}