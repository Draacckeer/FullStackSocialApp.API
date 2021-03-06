package com.example.fullstacksocialapp_api.app.domain.model.entity;

import com.example.fullstacksocialapp_api.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "publication_message")
public class PublicationComment extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    @Size(max = 1000)
    private String comment;

    //User
    private Long userid;
    private String username;
    private String userAvatar;

    // Message Location
    private Long publication; // ID
    private Long line;
    private Long level;
    private Long level1;
    private Long level2;
    private Long level3;

}
