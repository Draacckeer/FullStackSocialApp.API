package com.example.fullstacksocialapp_api.app.services;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Message;
import com.example.fullstacksocialapp_api.app.domain.persistence.MessageRepository;
import com.example.fullstacksocialapp_api.app.domain.service.MessageService;
import com.example.fullstacksocialapp_api.shared.exception.ResourceNotFoundException;
import com.example.fullstacksocialapp_api.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String ENTITY = "Message";
    private final MessageRepository messageRepository;
    private final Validator validator;

    public MessageServiceImpl(MessageRepository messageRepository, Validator validator) {
        this.messageRepository = messageRepository;
        this.validator = validator;
    }

    @Override
    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getByUserSenderId(Long userSenderId){
        return messageRepository.findByUserSenderid(userSenderId);
    }

    @Override
    public List<Message> getByUserReceiverId(Long userReceiverId){
        return messageRepository.findByUserReceiverid(userReceiverId);
    }

    @Override
    public List<Message> getByUserSenderIdAndUserReceiverId(Long userSenderId, Long userReceiverId){
        List<Message> messagesSent = messageRepository.findByUserSenderidAndUserReceiverid(userSenderId, userReceiverId);
        List<Message> messagesReceived = messageRepository.findByUserSenderidAndUserReceiverid(userReceiverId, userSenderId);
        List<Message> messages = new ArrayList<Message>();
        messages.addAll(messagesSent);
        messages.addAll(messagesReceived);
        return messages;
    }

    @Override
    public Message create(Message message) {
        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return messageRepository.save(message);
    }

    @Override
    public Message update(Long id, Message request) {
        Set<ConstraintViolation<Message>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return messageRepository.findById(id).map(message ->
            messageRepository.save(message.withContent(message.getContent())))
            .orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return messageRepository.findById(id).map(message -> {
            messageRepository.delete(message);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }
}
