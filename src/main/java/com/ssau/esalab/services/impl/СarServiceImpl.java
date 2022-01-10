package com.ssau.esalab.services.impl;

import com.ssau.esalab.jms.Sender;
import com.ssau.esalab.model.Car;
import com.ssau.esalab.repositories.СarRepository;
import com.ssau.esalab.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Repository
@Transactional
public class СarServiceImpl extends BaseServiceImpl<Car> implements CarService {

    private Sender sender;

    @Autowired
    public СarServiceImpl(СarRepository repository, Sender sender) {
        super(repository);
        this.sender = sender;
    }

    @Override
    public void save(Car car) {
        repository.save(car);
        sender.sendInsertEvent("Car",car);
    }

    @Override
    public void delete(Car car) {
        repository.delete(car);
        sender.sendDeleteEvent("Car",car);
    }
}
