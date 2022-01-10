package com.ssau.esalab.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(Integer id);
    List<T> findAll();
    void save(T obj);
    void delete(T obj);
}
