package com.example.fullstacksocialapp_api.security.domain.service;

import com.example.fullstacksocialapp_api.security.domain.model.entity.User;
import com.example.fullstacksocialapp_api.security.domain.service.communication.AuthenticateRequest;
import com.example.fullstacksocialapp_api.security.domain.service.communication.RegisterRequest;
import com.example.fullstacksocialapp_api.security.resources.UserResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends UserDetailsService {

    ResponseEntity<?> authenticate(AuthenticateRequest request);

    ResponseEntity<?>  register(RegisterRequest request);

    List<User> getAll();

    String getUsernameByToken(HttpServletRequest request, HttpServletResponse response);

    Long getUserIdByToken(HttpServletRequest request, HttpServletResponse response);

    User getUserById(Long id);

    User getUserByToken(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> likeUserIdByToken(Long id, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> unlikeUserIdByToken(Long userId, HttpServletRequest request, HttpServletResponse response);
}
