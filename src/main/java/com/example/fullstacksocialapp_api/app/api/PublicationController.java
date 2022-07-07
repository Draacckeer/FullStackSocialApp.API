package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.PublicationService;
import com.example.fullstacksocialapp_api.app.mapping.PublicationMapper;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/publication")
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationMapper mapper;

    public PublicationController(PublicationService publicationService, PublicationMapper mapper){
        this.publicationService = publicationService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PublicationResource> getAll(){
        return mapper.toResource(publicationService.getAll());
    }

    @PostMapping
    public PublicationResource create(@RequestBody CreatePublicationResource resource){
        return mapper.toResource(publicationService.create(mapper.toModel(resource)));
    }

    @PutMapping("{publicationId}")
    public PublicationResource update(@PathVariable Long publicationId,
                                      @RequestBody UpdatePublicationResource resource){
        return mapper.toResource(publicationService.update(publicationId, mapper.toModel(resource)));
    }

    @DeleteMapping("{publicationId}")
    public ResponseEntity<?> delete(@PathVariable Long publicationId){
        return publicationService.delete(publicationId);
    }
}
