package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationCommentRepository extends JpaRepository<PublicationComment, Long> {
    List<PublicationComment> findByPublication(Long publicationId);
    List<PublicationComment> findByLevel1(Long level1);
    List<PublicationComment> findByLevel1AndLevel2(Long level1, Long level2);
    List<PublicationComment> findByLevel1AndLevel2AndLevel3(Long level1, Long level2, Long level3);
}
