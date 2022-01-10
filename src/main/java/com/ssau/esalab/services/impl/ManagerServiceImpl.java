package com.ssau.esalab.services.impl;

import com.ssau.esalab.jms.Sender;
import com.ssau.esalab.model.Manager;
import com.ssau.esalab.repositories.ManagerRepository;
import com.ssau.esalab.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class ManagerServiceImpl extends BaseServiceImpl<Manager> implements ManagerService {

    private Sender sender;

    @Autowired
    public ManagerServiceImpl(ManagerRepository repository, Sender sender) {
        super(repository);
        this.sender = sender;
    }

    @Override
    public void save(Manager manager) {
        repository.save(manager);
        sender.sendInsertEvent("Manager",manager);
    }

    @Override
    public void delete(Manager manager) {
        repository.delete(manager);
        sender.sendDeleteEvent("Manager",manager);
    }
}
