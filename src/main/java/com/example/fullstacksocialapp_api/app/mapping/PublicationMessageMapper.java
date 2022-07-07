package com.example.fullstacksocialapp_api.app.mapping;

import com.example.fullstacksocialapp_api.app.domain.model.entity.PublicationMessage;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationMessageResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationMessageResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationMessageResource;
import com.example.fullstacksocialapp_api.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class PublicationMessageMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public PublicationMessageResource toResource(PublicationMessage model){return mapper.map(model, PublicationMessageResource.class);}

    public List<PublicationMessageResource> toResource(List<PublicationMessage> model){
        return mapper.mapList(model, PublicationMessageResource.class);
    }

    public PublicationMessage toModel(CreatePublicationMessageResource resource){
        return mapper.map(resource, PublicationMessage.class);
    }

    public PublicationMessage toModel(UpdatePublicationMessageResource resource){
        return mapper.map(resource, PublicationMessage.class);
    }
}
