package com.example.fullstacksocialapp_api.app.domain.service;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublicationService {
    List<Publication> getAll();
    Publication create(Publication publication);
    Publication update(Long id, Publication publication);
    ResponseEntity<?> delete(Long id);
}
