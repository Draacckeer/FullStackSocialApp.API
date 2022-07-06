package com.example.fullstacksocialapp_api.app.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "acme")
@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("api/v1/publication-message")
public class PublicationMessageController {

    private final WholesalerService wholesalerService;
    private final WholesalerMapper mapper;

    public WholesalerController(WholesalerService wholesalerService, WholesalerMapper mapper){
        this.wholesalerService = wholesalerService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<WholesalerResource> getAll(){
        return mapper.toResource(wholesalerService.getAll());
    }

    @GetMapping("{wholesalerId}")
    public WholesalerResource getWholesalerById(@PathVariable Long wholesalerId){
        return mapper.toResource(wholesalerService.getById(wholesalerId));
    }

    @PostMapping
    public WholesalerResource createWholesaler(@RequestBody CreateWholesalerResource resource){
        return mapper.toResource(wholesalerService.create(mapper.toModel(resource)));
    }

    @PutMapping("{wholesalerId}")
    public WholesalerResource updateWholesaler(@PathVariable Long wholesalerId,
                                               @RequestBody UpdateWholesalerResource resource){
        return mapper.toResource(wholesalerService.update(wholesalerId, mapper.toModel(resource)));
    }

    @DeleteMapping("{wholesalerId}")
    public ResponseEntity<?> deleteWholesaler(@PathVariable Long wholesalerId){
        return wholesalerService.delete(wholesalerId);
    }
}
