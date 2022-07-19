package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserSenderid(Long userSenderid);
    List<Message> findByUserReceiverid(Long userReceiverid);
    List<Message> findByUserSenderidAndUserReceiverid(Long userSenderid, Long userReceiverid);
}
