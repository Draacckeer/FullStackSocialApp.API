package com.example.fullstacksocialapp_api.app.domain.service;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationComment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublicationCommentService {
    List<PublicationComment> getAll();
    List<PublicationComment> getByPublicationId(Long publicationId);
    PublicationComment create(PublicationComment publicationComment);
    PublicationComment update(Long id, PublicationComment publicationComment);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> deleteByPublicationId(Long id);
    ResponseEntity<?> deleteByLevel1(Long level1);
    ResponseEntity<?> deleteByLevel1AndLevel2(Long level1, Long level2);
    ResponseEntity<?> deleteByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3);

}
