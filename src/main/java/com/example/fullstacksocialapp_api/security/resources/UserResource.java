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
    private List<RoleResource> roles;

}
