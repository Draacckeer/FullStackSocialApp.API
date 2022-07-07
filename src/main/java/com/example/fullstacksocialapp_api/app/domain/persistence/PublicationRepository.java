package com.example.fullstacksocialapp_api.app.domain.persistence;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
