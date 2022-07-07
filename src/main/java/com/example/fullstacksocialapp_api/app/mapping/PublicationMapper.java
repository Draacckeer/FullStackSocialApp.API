package com.example.fullstacksocialapp_api.app.mapping;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Publication;
import com.example.fullstacksocialapp_api.app.resources.publication.CreatePublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.PublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.UpdatePublicationResource;
import com.example.fullstacksocialapp_api.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class PublicationMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public PublicationResource toResource(Publication model){return mapper.map(model, PublicationResource.class);}

    public List<PublicationResource> toResource(List<Publication> model){
        return mapper.mapList(model, PublicationResource.class);
    }

    public Publication toModel(CreatePublicationResource resource){
        return mapper.map(resource, Publication.class);
    }

    public Publication toModel(UpdatePublicationResource resource){
        return mapper.map(resource, Publication.class);
    }

}
