package com.example.fullstacksocialapp_api.app.api;

import com.example.fullstacksocialapp_api.app.domain.service.PublicationCommentService;
import com.example.fullstacksocialapp_api.app.mapping.PublicationCommentMapper;
import com.example.fullstacksocialapp_api.app.resources.publication_message.CreatePublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.PublicationCommentResource;
import com.example.fullstacksocialapp_api.app.resources.publication_message.UpdatePublicationCommentResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<PublicationCommentResource> getAll(){
        return mapper.toResource(publicationCommentService.getAll());
    }

    @GetMapping("getByPublicationId/{publicationId}")
    public  List<PublicationCommentResource> getByPublicationId(@PathVariable Long publicationId){
        return mapper.toResource(publicationCommentService.getByPublicationId(publicationId));
    }

    @PostMapping
    public PublicationCommentResource create(@RequestBody CreatePublicationCommentResource resource){
        return mapper.toResource(publicationCommentService.create(mapper.toModel(resource)));
    }

    @PutMapping("{publicationCommentId}")
    public PublicationCommentResource update(@PathVariable Long publicationCommentId,
                                             @RequestBody UpdatePublicationCommentResource resource){
        return mapper.toResource(publicationCommentService.update(publicationCommentId, mapper.toModel(resource)));
    }

    @DeleteMapping("{publicationCommentId}")
    public ResponseEntity<?> delete(@PathVariable Long publicationCommentId){
        return publicationCommentService.delete(publicationCommentId);
    }

    @DeleteMapping("deleteByPublicationId/{publicationId}")
    public ResponseEntity<?> deleteByPublicationId(@PathVariable Long publicationId){
        return publicationCommentService.deleteByPublicationId(publicationId);
    }

    @DeleteMapping("deleteByLevel1/{level1}")
    public ResponseEntity<?> deleteByLevel1(@PathVariable Long level1){
        return publicationCommentService.deleteByLevel1(level1);
    }

    @DeleteMapping("deleteByLevel1AndLevel2/{level1}/{level2}")
    public ResponseEntity<?> deleteByLevel1AndLevel2(@PathVariable Long level1, @PathVariable Long level2){
        return publicationCommentService.deleteByLevel1AndLevel2(level1, level2);
    }

    @DeleteMapping("deleteByLevel1AndLevel2/{level1}/{level2}/{level3}")
    public ResponseEntity<?> deleteByLevel1AndLevel2(@PathVariable Long level1, @PathVariable Long level2, @PathVariable Long level3){
        return publicationCommentService.deleteByLevel1AndLevel2AndLevel3(level1, level2,level3);
    }
}
