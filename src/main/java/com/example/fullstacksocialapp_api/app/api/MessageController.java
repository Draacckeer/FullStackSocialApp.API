package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.MessageService;
import com.example.fullstacksocialapp_api.app.mapping.MessageMapper;
import com.example.fullstacksocialapp_api.app.resources.message.CreateMessageResource;
import com.example.fullstacksocialapp_api.app.resources.message.MessageResource;
import com.example.fullstacksocialapp_api.app.resources.message.UpdateMessageResource;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "acme")
@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper mapper;

    public MessageController(MessageService messageService, MessageMapper mapper){
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MessageResource> getAll(){
        return mapper.toResource(messageService.getAll());
    }

    @GetMapping("/userSenderId/{userSenderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MessageResource> getByUserSenderId(@PathVariable Long userSenderId){
        return mapper.toResource(messageService.getByUserSenderId(userSenderId));
    }

    @GetMapping("/userReceiverId/{userReceiverId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MessageResource> getByUserReceiverId(@PathVariable Long userReceiverId){
        return mapper.toResource(messageService.getByUserReceiverId(userReceiverId));
    }

    @GetMapping("/userSenderId/{userSenderId}/userReceiverId/{userReceiverId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MessageResource> getByUserSenderIdAndUserReceiverId(@PathVariable Long userSenderId, @PathVariable Long userReceiverId){
        return mapper.toResource(messageService.getByUserSenderIdAndUserReceiverId(userSenderId, userReceiverId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MessageResource create(@RequestBody CreateMessageResource resource){
        return mapper.toResource(messageService.create(mapper.toModel(resource)));
    }

    @PutMapping("{messageId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MessageResource update(@PathVariable Long messageId,
                                  @RequestBody UpdateMessageResource resource){
        return mapper.toResource(messageService.update(messageId, mapper.toModel(resource)));
    }

    @DeleteMapping("{messageId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long messageId){
        return messageService.delete(messageId);
    }

}
