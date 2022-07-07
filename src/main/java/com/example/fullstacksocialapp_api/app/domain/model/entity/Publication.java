package com.example.fullstacksocialapp_api.app.domain.model.entity;


import com.example.fullstacksocialapp_api.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "publication_message")
public class Publication extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String message;

}
