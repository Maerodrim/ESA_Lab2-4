package com.ssau.esalab.jms;


import com.ssau.esalab.model.Email;
import com.ssau.esalab.model.Event;
import com.ssau.esalab.services.EmailService;
import com.ssau.esalab.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.ssau.esalab.utils.StringConst.*;

@Component
public class Receiver {

    private EmailService emailService;
    private EventService eventService;

    @Autowired
    public Receiver(EmailService emailService, EventService eventService) {
        this.emailService = emailService;
        this.eventService = eventService;
    }

    @JmsListener(destination = receiverNameUpdate)
    public void receiveMassageUpdate(Event event) {
        receiveMassage(event);
    }

    @JmsListener(destination = receiverNameInsert)
    public void receiveMassageInsert(Event event) {
        receiveMassage(event);
    }

    @JmsListener(destination = receiverNameDelete)
    public void receiveMassageDelete(Event event) {
        receiveMassage(event);
    }

    private void receiveMassage(Event event) {
        eventService.save(event);
        String massage = String.format("%s happend", event.getAction());
        Email email = new Email(massage, "admin@gmail.com");
        emailService.save(email);
    }
}
