package com.example.fullstacksocialapp_api.app.domain.model.entity;


import javax.persistence.*;

public class PublicationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

}
