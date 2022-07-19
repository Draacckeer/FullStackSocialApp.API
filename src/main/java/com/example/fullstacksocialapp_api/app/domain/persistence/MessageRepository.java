package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserSenderid(Long userSenderid);
    List<Message> findByUserReceiverid(Long userReceiverid);

    @Query(value = "SELECT * FROM Message m WHERE m.user_receiverid = 2 AND m.user_senderid=1 OR m.user_senderid=2 AND m.user_receiverid=1",
            nativeQuery = true)
    List<Message> findByUserSenderidAndUserReceiverid(Long userSenderid, Long userReceiverid);
}
