package com.example.fullstacksocialapp_api.app.resources.publication;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePublicationResource {
    @Size(max = 1000)
    private String content;
}
