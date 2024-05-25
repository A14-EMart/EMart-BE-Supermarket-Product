package com.a14.emart.backendsp.service;
import com.a14.emart.backendsp.model.Supermarket;

import java.util.UUID;
public interface UpdateService<T> {
    Supermarket update(UUID entityId, T entity);
}