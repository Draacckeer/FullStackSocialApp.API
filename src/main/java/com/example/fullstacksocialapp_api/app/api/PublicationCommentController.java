package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.PublicationCommentService;
import com.example.fullstacksocialapp_api.app.mapping.PublicationCommentMapper;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationCommentResource;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "acme")
@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/publication_comments")
public class PublicationCommentController {
    private final PublicationCommentService publicationCommentService;
    private final PublicationCommentMapper mapper;

    public PublicationCommentController(PublicationCommentService publicationCommentService, PublicationCommentMapper mapper){
        this.publicationCommentService = publicationCommentService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<PublicationCommentResource> getAll(){
        return mapper.toResource(publicationCommentService.getAll());
    }

    @GetMapping("getByPublicationId/{publicationId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public  List<PublicationCommentResource> getByPublicationId(@PathVariable Long publicationId){
        return mapper.toResource(publicationCommentService.getByPublicationId(publicationId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PublicationCommentResource create(@RequestBody CreatePublicationCommentResource resource){
        return mapper.toResource(publicationCommentService.create(mapper.toModel(resource)));
    }

    @PutMapping("{publicationCommentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PublicationCommentResource update(@PathVariable Long publicationCommentId,
                                             @RequestBody UpdatePublicationCommentResource resource){
        return mapper.toResource(publicationCommentService.update(publicationCommentId, mapper.toModel(resource)));
    }

    @DeleteMapping("{publicationCommentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long publicationCommentId){
        return publicationCommentService.delete(publicationCommentId);
    }

    @DeleteMapping("deleteByPublicationId/{publicationId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteByPublicationId(@PathVariable Long publicationId){
        return publicationCommentService.deleteByPublicationId(publicationId);
    }

    @DeleteMapping("deleteByLevel1/{level1}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteByLevel1(@PathVariable Long level1){
        return publicationCommentService.deleteByLevel1(level1);
    }

    @DeleteMapping("deleteByLevel1AndLevel2/{level1}/{level2}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteByLevel1AndLevel2(@PathVariable Long level1, @PathVariable Long level2){
        return publicationCommentService.deleteByLevel1AndLevel2(level1, level2);
    }

    @DeleteMapping("deleteByLevel1AndLevel2/{level1}/{level2}/{level3}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteByLevel1AndLevel2(@PathVariable Long level1, @PathVariable Long level2, @PathVariable Long level3){
        return publicationCommentService.deleteByLevel1AndLevel2AndLevel3(level1, level2,level3);
    }
}
