package com.example.fullstacksocialapp_api.app.resources.message;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMessageResource {
    @Size(max = 1000)
    private String content;
}
