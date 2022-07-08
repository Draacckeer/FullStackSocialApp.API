package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationMessageRepository extends JpaRepository<PublicationMessage, Long> {
    List<PublicationMessage> findByPublication(Long publicationId);
    List<PublicationMessage> findByLevel1(Long level1);
    List<PublicationMessage> findByLevel1AndLevel2(Long level1, Long level2);
    List<PublicationMessage> findByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3);
}
