package com.example.fullstacksocialapp_api.app.domain.service;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublicationService {
    List<Publication> getAll();
    Publication create(Publication publicationMessage);
    Publication update(Long id, Publication publicationMessage);
    ResponseEntity<?> delete(Long id);
}
