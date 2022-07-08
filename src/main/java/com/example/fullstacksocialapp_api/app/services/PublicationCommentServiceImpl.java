package com.example.fullstacksocialapp_api.app.services;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationComment;
import com.example.fullstacksocialapp_api.app.domain.persistence.PublicationCommentRepository;
import com.example.fullstacksocialapp_api.app.domain.service.PublicationCommentService;
import com.example.fullstacksocialapp_api.shared.exception.ResourceNotFoundException;
import com.example.fullstacksocialapp_api.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PublicationCommentServiceImpl implements PublicationCommentService {
    private static final String ENTITY = "Publication";

    private final PublicationCommentRepository publicationCommentRepository;
    private final Validator validator;

    public PublicationCommentServiceImpl(PublicationCommentRepository publicationCommentRepository, Validator validator) {

        this.publicationCommentRepository = publicationCommentRepository;

        this.validator = validator;
    }

    @Override
    public List<PublicationComment> getAll() {
        return publicationCommentRepository.findAll();
    }

    @Override
    public List<PublicationComment> getByPublicationId(Long publicationId) {

        return publicationCommentRepository.findByPublication(publicationId);
    }

    @Override
    public PublicationComment create(PublicationComment publicationComment) {
        Set<ConstraintViolation<PublicationComment>> violations = validator.validate(publicationComment);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);


        return publicationCommentRepository.save(publicationComment);
    }

    @Override
    public PublicationComment update(Long id, PublicationComment request) {

        Set<ConstraintViolation<PublicationComment>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return publicationCommentRepository.findById(id).map(publicationComment ->
                        publicationCommentRepository.save(publicationComment
                                .withComment(request.getComment())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));

    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return publicationCommentRepository.findById(id).map(publicationComment -> {
            publicationCommentRepository.delete(publicationComment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }

    @Override
    public ResponseEntity<?> deleteByPublicationId(Long id) {
        publicationCommentRepository.deleteAll(publicationCommentRepository.findByPublication(id));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1(Long level1) {
        publicationCommentRepository.deleteAll(publicationCommentRepository.findByLevel1(level1));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1AndLevel2(Long level1, Long level2) {
        publicationCommentRepository.deleteAll(publicationCommentRepository.findByLevel1AndLevel2(level1, level2));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3) {
        publicationCommentRepository.deleteAll(publicationCommentRepository.findByLevel1AndLevel2AndLevel3(level1, level2, level3));
        return ResponseEntity.ok().build();
    }
}
