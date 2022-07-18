package com.example.fullstacksocialapp_api.security.resources;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserRequestOfFriendsResource {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private Long likes;
}
