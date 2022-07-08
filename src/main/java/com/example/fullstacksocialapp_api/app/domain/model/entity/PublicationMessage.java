package com.example.fullstacksocialapp_api.app.domain.model.entity;

import com.example.fullstacksocialapp_api.shared.domain.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "publication_message")
public class PublicationMessage extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Long publication; // ID
    private Long order;
    private Long level;
    private Long level1;
    private Long level2;
    private Long level3;

}
