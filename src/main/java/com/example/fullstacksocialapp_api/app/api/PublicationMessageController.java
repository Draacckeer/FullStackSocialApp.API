package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.PublicationMessageService;
import com.example.fullstacksocialapp_api.app.domain.service.PublicationService;
import com.example.fullstacksocialapp_api.app.mapping.PublicationMapper;
import com.example.fullstacksocialapp_api.app.mapping.PublicationMessageMapper;
import com.example.fullstacksocialapp_api.app.resources.publication.CreatePublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.PublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.UpdatePublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationMessageResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationMessageResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationMessageResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/publication_message")
public class PublicationMessageController {

    private final PublicationMessageService publicationMessageService;
    private final PublicationMessageMapper mapper;

    public PublicationMessageController(PublicationMessageService publicationMessageService, PublicationMessageMapper mapper){
        this.publicationMessageService = publicationMessageService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PublicationMessageResource> getAll(){
        return mapper.toResource(publicationMessageService.getAll());
    }

    @PostMapping
    public PublicationMessageResource create(@RequestBody CreatePublicationMessageResource resource){
        return mapper.toResource(publicationMessageService.create(mapper.toModel(resource)));
    }

    @PutMapping("{publicationMessageId}")
    public PublicationMessageResource update(@PathVariable Long publicationMessageId,
                                      @RequestBody UpdatePublicationMessageResource resource){
        return mapper.toResource(publicationMessageService.update(publicationMessageId, mapper.toModel(resource)));
    }

    @DeleteMapping("{publicationMessageId}")
    public ResponseEntity<?> delete(@PathVariable Long publicationMessageId){
        return publicationMessageService.delete(publicationMessageId);
    }
}
