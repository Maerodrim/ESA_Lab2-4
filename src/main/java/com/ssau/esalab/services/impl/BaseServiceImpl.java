package com.ssau.esalab.services.impl;

import com.ssau.esalab.services.BaseService;
import com.ssau.esalab.utils.ConverterUtils;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T> implements BaseService<T>{

    protected final CrudRepository repository;

    protected BaseServiceImpl(CrudRepository repository) {
        this.repository = repository;
    }

    public Optional<T> findById(Integer id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return ConverterUtils.iterableToArrayList(repository.findAll());
    }

    public void save(T obj) {
        repository.save(obj);
    }

    public void delete(T obj) {
        repository.delete(obj);
    }
}
