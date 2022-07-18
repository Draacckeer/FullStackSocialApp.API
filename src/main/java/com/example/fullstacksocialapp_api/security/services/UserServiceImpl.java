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
import org.springframework.data.domain.Sort;
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
                    .withRoles(roles).withAvatar(request.getAvatar()).withLikes(0L).withLikesList(new HashSet<>())
                    .withRequestFriendsList(new HashSet<>()).withRequestOfFriendsList(new HashSet<>())
                    .withFriendsList(new HashSet<>());
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
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "likes"));
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

    @Override
    public ResponseEntity<?> unlikeUserIdByToken(Long id, HttpServletRequest request, HttpServletResponse response){
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), id)){
                    return ResponseEntity.badRequest().body("You can't unlike yourself");
                }
                User userToUnlike = userRepository.findById(id).orElse(null);
                if(userToUnlike != null){
                    if(!user.getLikesList().contains(userToUnlike)){
                        return ResponseEntity.badRequest().body("You didn't like this user");
                    }
                    Set<User> likesList = new HashSet<>(user.getLikesList());
                    likesList.remove(userToUnlike);
                    user.setLikesList(likesList);
                    userToUnlike.setLikes(userToUnlike.getLikes() - 1);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToUnlike, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to unlike not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @Override
    public ResponseEntity<?> requestFriendByToken(Long userId, HttpServletRequest request, HttpServletResponse response){
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), userId)){
                    return ResponseEntity.badRequest().body("You can't request yourself");
                }
                User userToRequest = userRepository.findById(userId).orElse(null);
                if(userToRequest != null){
                    if(user.getFriendsList().contains(userToRequest)){
                        return ResponseEntity.badRequest().body("You already have this user in your friends list");
                    }
                    if(userToRequest.getFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user already has you in his friends list");
                    }
                    if(user.getRequestFriendsList().contains(userToRequest)){
                        return ResponseEntity.badRequest().body("You already have this user in your requests list");
                    }
                    if(userToRequest.getRequestFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user already has you in his requests list");
                    }
                    Set<User> requestFriendsList = new HashSet<>(user.getRequestFriendsList());
                    requestFriendsList.add(userToRequest);
                    user.setRequestFriendsList(requestFriendsList);
                    Set<User> requestOfFriendsList = new HashSet<>(userToRequest.getRequestOfFriendsList());
                    requestOfFriendsList.add(user);
                    userToRequest.setRequestOfFriendsList(requestOfFriendsList);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToRequest, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to request not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @Override
    public ResponseEntity<?> unrequestFriendByToken(Long userId, HttpServletRequest request, HttpServletResponse response){
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), userId)){
                    return ResponseEntity.badRequest().body("You can't unrequest yourself");
                }
                User userToUnrequest = userRepository.findById(userId).orElse(null);
                if(userToUnrequest != null){
                    if(!user.getRequestFriendsList().contains(userToUnrequest)){
                        return ResponseEntity.badRequest().body("You don't have this user in your requests list");
                    }
                    if(!userToUnrequest.getRequestOfFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user doesn't have you in his requests list");
                    }
                    Set<User> requestFriendsList = new HashSet<>(user.getRequestFriendsList());
                    requestFriendsList.remove(userToUnrequest);
                    user.setRequestFriendsList(requestFriendsList);
                    Set<User> requestOfFriendsList = new HashSet<>(userToUnrequest.getRequestOfFriendsList());
                    requestOfFriendsList.remove(user);
                    userToUnrequest.setRequestOfFriendsList(requestOfFriendsList);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToUnrequest, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to unrequest not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @Override
    public ResponseEntity<?> acceptFriendByToken(Long userId, HttpServletRequest request, HttpServletResponse response){
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), userId)){
                    return ResponseEntity.badRequest().body("You can't accept yourself");
                }
                User userToAccept = userRepository.findById(userId).orElse(null);
                if(userToAccept != null){
                    if(user.getFriendsList().contains(userToAccept)){
                        return ResponseEntity.badRequest().body("You already have this user in your friends list");
                    }
                    if(userToAccept.getFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user already has you in his friends list");
                    }
                    if(!userToAccept.getRequestOfFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user didn't request you");
                    }
                    Set<User> requestOfFriendsList = new HashSet<>(user.getRequestOfFriendsList());
                    requestOfFriendsList.remove(userToAccept);
                    user.setRequestFriendsList(requestOfFriendsList);
                    Set<User> requestFriendsList = new HashSet<>(userToAccept.getRequestFriendsList());
                    requestFriendsList.remove(user);
                    userToAccept.setRequestOfFriendsList(requestFriendsList);
                    Set<User> friendsList = new HashSet<>(user.getFriendsList());
                    friendsList.add(userToAccept);
                    user.setFriendsList(friendsList);
                    Set<User> friendsOfList = new HashSet<>(userToAccept.getFriendsList());
                    friendsOfList.add(user);
                    userToAccept.setFriendsList(friendsOfList);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToAccept, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to accept not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @Override
    public ResponseEntity<?> rejectFriendByToken(Long userId, HttpServletRequest request, HttpServletResponse response){
        String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), userId)){
                    return ResponseEntity.badRequest().body("You can't reject yourself");
                }
                User userToReject = userRepository.findById(userId).orElse(null);
                if(userToReject != null){
                    if(!userToReject.getRequestOfFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user didn't request you");
                    }
                    Set<User> requestOfFriendsList = new HashSet<>(user.getRequestOfFriendsList());
                    requestOfFriendsList.remove(userToReject);
                    user.setRequestFriendsList(requestOfFriendsList);
                    Set<User> requestFriendsList = new HashSet<>(userToReject.getRequestFriendsList());
                    requestFriendsList.remove(user);
                    userToReject.setRequestOfFriendsList(requestFriendsList);
                    userRepository.save(userToReject);
                    UserResource resource = mapper.map(userToReject, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to reject not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @Override
    public ResponseEntity<?> unfriendByToken(Long userId, HttpServletRequest request, HttpServletResponse response){
    String token = jwtAuthenticationFilter.parseTokenFrom(request);
        if (token != null && handler.validateToken(token)){
            logger.info("Token: {}", token);
            User user = userRepository.findByUsername(handler.getUsernameFrom(token)).orElse(null);
            if(user != null){
                if(Objects.equals(user.getId(), userId)){
                    return ResponseEntity.badRequest().body("You can't unfriend yourself");
                }
                User userToUnfriend = userRepository.findById(userId).orElse(null);
                if(userToUnfriend != null){
                    if(!user.getFriendsList().contains(userToUnfriend)){
                        return ResponseEntity.badRequest().body("You don't have this user in your friends list");
                    }
                    if(!userToUnfriend.getFriendsList().contains(user)){
                        return ResponseEntity.badRequest().body("This user doesn't have you in his friends list");
                    }
                    Set<User> friendsList = new HashSet<>(user.getFriendsList());
                    friendsList.remove(userToUnfriend);
                    user.setFriendsList(friendsList);
                    Set<User> friendsOfList = new HashSet<>(userToUnfriend.getFriendsOfList());
                    friendsOfList.remove(user);
                    userToUnfriend.setFriendsOfList(friendsOfList);
                    userRepository.save(user);
                    UserResource resource = mapper.map(userToUnfriend, UserResource.class);
                    return ResponseEntity.ok(resource);
                }
                else{
                    return ResponseEntity.badRequest().body("User to unfriend not found");
                }
            }
            else{
                return ResponseEntity.badRequest().body("User not found");
            }
        }
        return ResponseEntity.badRequest().body("Error");
    }

}
