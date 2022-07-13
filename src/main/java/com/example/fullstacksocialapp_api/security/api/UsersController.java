package com.example.fullstacksocialapp_api.security.api;

import com.example.fullstacksocialapp_api.security.domain.service.UserService;
import com.example.fullstacksocialapp_api.security.domain.service.communication.AuthenticateRequest;
import com.example.fullstacksocialapp_api.security.domain.service.communication.RegisterRequest;
import com.example.fullstacksocialapp_api.security.mapping.UserMapper;
import com.example.fullstacksocialapp_api.security.resources.UserResource;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "acme")
@Tag(name="Users", description = "Create, read, update and delete users")
@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users/auth")
public class UsersController {

    private final UserService userService;
    private final UserMapper mapper;

    public UsersController(UserService userService, UserMapper mapper){
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody
                                              AuthenticateRequest request){
        return userService.authenticate(request);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody
                                          RegisterRequest request){
        return userService.register(request);
    }


    @GetMapping("/get-all")
    public List<UserResource> getAllUsers(){
        return mapper.toResource(userService.getAll());
    }

    @GetMapping("/get-username-by-token")
    public String getUsernameByToken(HttpServletRequest request, HttpServletResponse response){
        return userService.getUsernameByToken(request, response);
    }

}