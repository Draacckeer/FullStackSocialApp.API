package com.example.fullstacksocialapp_api.security.domain.model.entity;

import com.example.fullstacksocialapp_api.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@With
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    
    @NotBlank
    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String avatar;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String password;

    private Long likes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "like_user_id"))
    private Set<User> likesList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



}
