package com.example.fullstacksocialapp_api.app.domain.service;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublicationMessageService {
    List<PublicationMessage> getAll();
    List<PublicationMessage> getByPublicationId(Long publicationId);
    PublicationMessage create(PublicationMessage publicationMessage);
    PublicationMessage update(Long id, PublicationMessage publicationMessage);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> deleteByPublicationId(Long id);
    ResponseEntity<?> deleteByLevel1(Long level1);
    ResponseEntity<?> deleteByLevel1AndLevel2(Long level1, Long level2);
    ResponseEntity<?> deleteByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3);

}
