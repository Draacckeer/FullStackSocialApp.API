package com.example.fullstacksocialapp_api.app.resources.publication_message;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePublicationCommentResource {
    @Size(max = 1000)
    private String comment;
}
