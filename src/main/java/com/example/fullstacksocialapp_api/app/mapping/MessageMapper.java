package com.example.fullstacksocialapp_api.app.mapping;

import com.example.fullstacksocialapp_api.app.domain.model.entity.Message;
import com.example.fullstacksocialapp_api.app.resources.message.CreateMessageResource;
import com.example.fullstacksocialapp_api.app.resources.message.MessageResource;
import com.example.fullstacksocialapp_api.app.resources.message.UpdateMessageResource;
import com.example.fullstacksocialapp_api.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class MessageMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public MessageResource toResource(Message model){return mapper.map(model, MessageResource.class);}

    public List<MessageResource> toResource(List<Message> model){
        return mapper.mapList(model, MessageResource.class);
    }

    public Message toModel(CreateMessageResource resource){
        return mapper.map(resource, Message.class);
    }

    public Message toModel(UpdateMessageResource resource){
        return mapper.map(resource, Message.class);
    }
}
