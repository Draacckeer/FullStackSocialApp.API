package com.example.fullstacksocialapp_api.security.services;

import com.example.fullstacksocialapp_api.security.domain.model.entity.Role;
import com.example.fullstacksocialapp_api.security.domain.model.entity.User;
import com.example.fullstacksocialapp_api.security.domain.model.enumeration.Roles;
import com.example.fullstacksocialapp_api.security.domain.persistence.RoleRepository;
import com.example.fullstacksocialapp_api.security.domain.persistence.UserRepository;
import com.example.fullstacksocialapp_api.security.domain.service.UserService;
import com.example.fullstacksocialapp_api.security.domain.service.communication.*;
import com.example.fullstacksocialapp_api.security.middleware.JwtAuthenticationFilter;
import com.example.fullstacksocialapp_api.security.middleware.JwtHandler;
import com.example.fullstacksocialapp_api.security.middleware.UserDetailsImpl;
import com.example.fullstacksocialapp_api.security.resources.AuthenticateResource;
import com.example.fullstacksocialapp_api.security.resources.UserLikeResource;
import com.example.fullstacksocialapp_api.security.resources.UserResource;
import com.example.fullstacksocialapp_api.shared.mapping.EnhancedModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(
            UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtHandler handler;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EnhancedModelMapper mapper;

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = handler.generateToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl)  authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthenticateResource resource = mapper.map(userDetails, AuthenticateResource.class);
            resource.setRoles(roles);
            resource.setToken(token);

            AuthenticateResponse response = new AuthenticateResponse(resource);
            return ResponseEntity.ok(response.getResource());

        }catch (Exception e) {
            AuthenticateResponse response = new AuthenticateResponse(String.format(
                    "An error occurred while authenticating: %s", e.getMessage()));
            return ResponseEntity.badRequest().body(response.getMessage());

        }
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            AuthenticateResponse response = new AuthenticateResponse("Username is already used");
            return ResponseEntity.badRequest().body(response.getMessage());
        }

        if (userRepository.existsByEmail(request.getEmail())){
            AuthenticateResponse response = new AuthenticateResponse("Email is already used");
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        try{
            Set<String> roleStringSet = request.getRoles();
            Set<Role> roles = new HashSet<>();

            if(roleStringSet == null){
                roleRepository.findByName(Roles.ROLE_USER)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
            }else{
                roleStringSet.forEach(roleString ->
                        roleRepository.findByName(Roles.valueOf(roleString))
                                .map(roles::add)
                                .orElseThrow(() -> new RuntimeException("Roles not found")));
            }
            logger.info("Roles: {}", roles);

            User user = new User().withUsername(request.getUsername())
                    .withEmail(request.getEmail()).withPassword(encoder.encode(request.getPassword()))
                    .withRoles(roles).withAvatar(request.getAvatar()).withLikes(0L).withLikesList(new HashSet<>());
            userRepository.save(user);

            UserResource resource = mapper.map(user, UserResource.class);
            RegisterResponse response = new RegisterResponse(resource);
            return ResponseEntity.ok(response.getResource());

        }catch (Exception e){
            RegisterResponse response = new RegisterResponse(e.getMessage());
            return ResponseEntity.badRequest().body(response.getMessage());

        }

    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(
                        "User not found with username: %s", username)));
        return UserDetailsImpl.build(user);
    }

    @Override
    public String getUsernameByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            return handler.getUsernameFrom(token);
        }
        return null;
    }

    @Override
    public Long getUserIdByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            return userRepository.findByUsername(handler.getUsernameFrom(token)).map(User::getId).orElse(null);
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            return userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> likeUserIdByToken(Long id, HttpServletRequest request, HttpServletResponse response) {
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), id)){
                    return ResponseEntity.badRequest().body("You can't like yourself");
                }
                User userToLike = userRepository.findById(id).orElse(null);
                if(userToLike != null){
                    if(user.getLikesList().contains(userToLike)){
                        return ResponseEntity.badRequest().body("You already liked this user");
                    }
                    Set<User> likesList = new HashSet<>(user.getLikesList());
                    likesList.add(userToLike);
                    user.setLikesList(likesList);
                    userToLike.setLikes(userToLike.getLikes() + 1);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToLike, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to like not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

}
