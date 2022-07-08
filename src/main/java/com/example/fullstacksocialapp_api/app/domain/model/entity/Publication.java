package com.example.fullstacksocialapp_api.app.domain.model.entity;

import com.example.fullstacksocialapp_api.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "publication")
public class Publication extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

}
