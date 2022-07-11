package com.example.fullstacksocialapp_api.security.domain.service;

import com.example.fullstacksocialapp_api.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {

    void seed();

    List<Role> getAll();
}
