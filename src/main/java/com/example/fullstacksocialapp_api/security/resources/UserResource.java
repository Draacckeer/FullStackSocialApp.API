package com.example.fullstacksocialapp_api.security.resources;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserResource {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private Long likes;
    private List<UserLikeResource> userLikes;
    private List<UserLikeResource> userRequestFriends;
    private List<UserLikeResource> userRequestOfFriends;
    private List<UserLikeResource> userFriends;
    private List<UserLikeResource> userOfFriends;
    private List<RoleResource> roles;

}
