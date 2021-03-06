package com.example.fullstacksocialapp_api.app.resources.publication_message;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class PublicationCommentResource {
    private Long id;
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
    private String createdAt;
    private String updatedAt;
}
