package com.example.fullstacksocialapp_api.app.resources.publication_message;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class PublicationMessageResource {
    private Long id;
    private String message;
    private Long publication;
}
