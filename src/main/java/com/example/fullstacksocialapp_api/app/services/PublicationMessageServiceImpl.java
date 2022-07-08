package com.example.fullstacksocialapp_api.app.services;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationMessage;
import com.example.fullstacksocialapp_api.app.domain.persistence.PublicationMessageRepository;
import com.example.fullstacksocialapp_api.app.domain.persistence.PublicationRepository;
import com.example.fullstacksocialapp_api.app.domain.service.PublicationMessageService;
import com.example.fullstacksocialapp_api.app.domain.service.PublicationService;
import com.example.fullstacksocialapp_api.shared.exception.ResourceNotFoundException;
import com.example.fullstacksocialapp_api.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicationMessageServiceImpl implements PublicationMessageService {
    private static final String ENTITY = "Publication";

    private final PublicationMessageRepository publicationMessageRepository;
    private final Validator validator;

    public PublicationMessageServiceImpl(PublicationMessageRepository publicationMessageRepository, Validator validator) {

        this.publicationMessageRepository = publicationMessageRepository;

        this.validator = validator;
    }

    @Override
    public List<PublicationMessage> getAll() {
        return publicationMessageRepository.findAll();
    }

    @Override
    public List<PublicationMessage> getByPublicationId(Long publicationId) {

        return publicationMessageRepository.findByPublication(publicationId);
    }

    @Override
    public PublicationMessage create(PublicationMessage publicationMessage) {
        Set<ConstraintViolation<PublicationMessage>> violations = validator.validate(publicationMessage);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);


        return publicationMessageRepository.save(publicationMessage);
    }

    @Override
    public PublicationMessage update(Long id, PublicationMessage request) {

        Set<ConstraintViolation<PublicationMessage>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return publicationMessageRepository.findById(id).map(publicationMessage ->
                        publicationMessageRepository.save(publicationMessage
                                .withMessage(request.getMessage())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));

    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return publicationMessageRepository.findById(id).map(publicationMessage -> {
            publicationMessageRepository.delete(publicationMessage);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }

    @Override
    public ResponseEntity<?> deleteByPublicationId(Long id) {
        publicationMessageRepository.deleteAll(publicationMessageRepository.findByPublication(id));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1(Long level1) {
        publicationMessageRepository.deleteAll(publicationMessageRepository.findByLevel1(level1));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1AndLevel2(Long level1, Long level2) {
        publicationMessageRepository.deleteAll(publicationMessageRepository.findByLevel1AndLevel2(level1, level2));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3) {
        publicationMessageRepository.deleteAll(publicationMessageRepository.findByLevel1AndLevel2AndLevel3(level1, level2, level3));
        return ResponseEntity.ok().build();
    }
}
