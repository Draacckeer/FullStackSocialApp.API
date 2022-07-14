package com.example.fullstacksocialapp_api.security.domain.resources;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserPublicationResource {

    private Long userid;
    private String username;
}
