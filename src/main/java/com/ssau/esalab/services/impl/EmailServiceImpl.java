package com.ssau.esalab.services.impl;

import com.ssau.esalab.model.Email;
import com.ssau.esalab.model.Model;
import com.ssau.esalab.repositories.EmailRepository;
import com.ssau.esalab.services.EmailService;
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
public class EmailServiceImpl extends BaseServiceImpl<Email> implements EmailService {

    @Autowired
    public EmailServiceImpl(EmailRepository repository) {
        super(repository);
    }
}
