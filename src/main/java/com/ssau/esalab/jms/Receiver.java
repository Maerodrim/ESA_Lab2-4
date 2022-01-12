package com.ssau.esalab.jms;


import com.ssau.esalab.model.Email;
import com.ssau.esalab.model.Event;
import com.ssau.esalab.services.EmailService;
import com.ssau.esalab.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class Receiver {

    private EmailService emailService;
    private EventService eventService;

    @Autowired
    public Receiver(EmailService emailService, EventService eventService) {
        this.emailService = emailService;
        this.eventService = eventService;
    }

    protected void saveEvent(Event event) {
        eventService.save(event);
    }

    protected void receiveMassage(Event event) {
        String massage = String.format("%s happend", event.getAction());
        Email email = new Email(massage, "admin@gmail.com");
        emailService.save(email);
    }
}
