package com.example.fullstacksocialapp_api.app.resources.publication;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class PublicationResource {
    private Long id;
    private Long userid;
    private String username;
    private String userAvatar;
    private String content;
    private String createdAt;
    private String updatedAt;
}
