package com.example.fullstacksocialapp_api.app.mapping;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationComment;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationCommentResource;
import com.example.fullstacksocialapp_api.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class PublicationCommentMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public PublicationCommentResource toResource(PublicationComment model){return mapper.map(model, PublicationCommentResource.class);}

    public List<PublicationCommentResource> toResource(List<PublicationComment> model){
        return mapper.mapList(model, PublicationCommentResource.class);
    }

    public PublicationComment toModel(CreatePublicationCommentResource resource){
        return mapper.map(resource, PublicationComment.class);
    }

    public PublicationComment toModel(UpdatePublicationCommentResource resource){
        return mapper.map(resource, PublicationComment.class);
    }
}
