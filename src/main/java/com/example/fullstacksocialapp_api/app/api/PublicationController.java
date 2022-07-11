package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.PublicationService;
import com.example.fullstacksocialapp_api.app.mapping.PublicationMapper;
import com.example.fullstacksocialapp_api.app.resources.publication.CreatePublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.PublicationResource;
import com.example.fullstacksocialapp_api.app.resources.publication.UpdatePublicationResource;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "acme")
@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationMapper mapper;

    public PublicationController(PublicationService publicationService, PublicationMapper mapper){
        this.publicationService = publicationService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<PublicationResource> getAll(){
        return mapper.toResource(publicationService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PublicationResource create(@RequestBody CreatePublicationResource resource){
        return mapper.toResource(publicationService.create(mapper.toModel(resource)));
    }

    @PutMapping("{publicationId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PublicationResource update(@PathVariable Long publicationId,
                                      @RequestBody UpdatePublicationResource resource){
        return mapper.toResource(publicationService.update(publicationId, mapper.toModel(resource)));
    }

    @DeleteMapping("{publicationId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long publicationId){
        return publicationService.delete(publicationId);
    }
}
