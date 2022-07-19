package com.example.fullstacksocialapp_api.app.domain.service;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {
    List<Message> getAll();
    List<Message> getByUserSenderId(Long userSenderId);
    List<Message> getByUserReceiverId(Long userReceiverId);
    List<Message> getByUserSenderIdAndUserReceiverId(Long userSenderId, Long userReceiverId);
    Message create(Message message);
    Message update(Long id, Message message);
    ResponseEntity<?> delete(Long id);
}
