package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationMessageRepository extends JpaRepository<PublicationMessage, Long> {
    List<PublicationMessage> findByPublication(Long publicationId);
}
