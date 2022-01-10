package com.ssau.esalab.services.impl;

import com.ssau.esalab.jms.Sender;
import com.ssau.esalab.model.Model;
import com.ssau.esalab.repositories.ModelRepository;
import com.ssau.esalab.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class ModelServiceImpl extends BaseServiceImpl<Model> implements ModelService {
    private Sender sender;

    @Autowired
    public ModelServiceImpl(ModelRepository repository,Sender sender) {
        super(repository);
        this.sender = sender;
    }

    @Override
    public void save(Model model) {
        repository.save(model);
        sender.sendInsertEvent("Car", model);

    }

    @Override
    public void delete(Model model) {
        repository.delete(model);
        sender.sendDeleteEvent("Car", model);

    }
}
