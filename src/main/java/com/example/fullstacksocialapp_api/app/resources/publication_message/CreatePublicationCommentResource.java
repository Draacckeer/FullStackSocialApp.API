package com.example.fullstacksocialapp_api.app.resources.publication_message;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreatePublicationCommentResource {

    @Size(max = 1000)
    private String comment;
    private Long userid;
    private String username;
    private String userAvatar;
    private Long publication; // ID
    private Long line;
    private Long level;
    private Long level1;
    private Long level2;
    private Long level3;
}
