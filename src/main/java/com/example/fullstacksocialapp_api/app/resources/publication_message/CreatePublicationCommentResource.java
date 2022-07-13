package com.example.fullstacksocialapp_api.app.resources.publication_message;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreatePublicationCommentResource {

    private String comment;
    private Long user;
    private String username;
    private Long publication; // ID
    private Long line;
    private Long level;
    private Long level1;
    private Long level2;
    private Long level3;
}
