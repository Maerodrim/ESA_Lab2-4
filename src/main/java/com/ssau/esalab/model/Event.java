package com.ssau.esalab.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="myEvent")
@Data
@NoArgsConstructor

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="action")
    private String action;
    @Column(name="entity")
    private String entity;
    @Column(name="value")
    private String value;

    public Event(String action, String entity,String value) {
        this.action = action;
        this.entity = entity;
        this.value = value;


    }
}
