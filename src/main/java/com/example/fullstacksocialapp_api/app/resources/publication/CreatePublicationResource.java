package com.example.fullstacksocialapp_api.app.resources.publication;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreatePublicationResource {

    private Long userid;
    private String username;
    private String title;
    private String content;
}
