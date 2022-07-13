package com.example.fullstacksocialapp_api.app.services;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import com.example.fullstacksocialapp_api.app.domain.persistence.PublicationRepository;
import com.example.fullstacksocialapp_api.app.domain.service.PublicationService;
import com.example.fullstacksocialapp_api.shared.exception.ResourceNotFoundException;
import com.example.fullstacksocialapp_api.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PublicationServiceImpl implements PublicationService {
    private static final String ENTITY = "Publication";

    private final PublicationRepository publicationRepository;
    private final Validator validator;

    public PublicationServiceImpl(PublicationRepository publicationRepository, Validator validator) {

        this.publicationRepository = publicationRepository;

        this.validator = validator;
    }

    @Override
    public List<Publication> getAll() {
        return publicationRepository.findAll();
    }


    @Override
    public Publication create(Publication publication) {
        Set<ConstraintViolation<Publication>> violations = validator.validate(publication);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);


        return publicationRepository.save(publication);
    }

    @Override
    public Publication update(Long id, Publication request) {

        Set<ConstraintViolation<Publication>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return publicationRepository.findById(id).map(publication ->
                        publicationRepository.save(publication
                                .withTitle(request.getTitle())
                                .withContent(request.getContent())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));

    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return publicationRepository.findById(id).map(publication -> {
            publicationRepository.delete(publication);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }
}
