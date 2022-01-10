package com.ssau.esalab.services.impl;


import com.ssau.esalab.model.Event;
import com.ssau.esalab.model.Model;
import com.ssau.esalab.repositories.EventRepository;
import com.ssau.esalab.services.EventService;
import com.ssau.esalab.utils.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService {

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }
}
