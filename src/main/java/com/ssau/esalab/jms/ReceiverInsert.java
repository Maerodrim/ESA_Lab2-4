package com.ssau.esalab.jms;

import com.ssau.esalab.model.Event;
import com.ssau.esalab.services.EmailService;
import com.ssau.esalab.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.ssau.esalab.utils.StringConst.receiverNameInsert;

@Component
public class ReceiverInsert extends Receiver {

    @Autowired
    public ReceiverInsert(EmailService emailService, EventService eventService) {
        super(emailService, eventService);
    }

    @JmsListener(destination = receiverNameInsert)
    public void receiveMassageUpdate(Event event) {
        saveEvent(event);
        receiveMassage(event);
    }
}
