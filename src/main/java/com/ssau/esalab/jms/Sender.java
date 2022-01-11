package com.ssau.esalab.jms;

import com.ssau.esalab.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.ssau.esalab.utils.StringConst.*;

@Component
public class Sender {
    private JmsTemplate jmsTemplate;


    @Autowired
    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("Update", entity, value.toString());
        jmsTemplate.convertAndSend(receiverNameUpdate, event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("Insert", entity, value.toString());
        jmsTemplate.convertAndSend(receiverNameInsert, event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("Delete", entity, value.toString());
        jmsTemplate.convertAndSend(receiverNameDelete, event);
    }

}
