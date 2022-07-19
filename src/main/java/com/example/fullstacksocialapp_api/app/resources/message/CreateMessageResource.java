package com.example.fullstacksocialapp_api.app.resources.message;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageResource {
    private Long userSenderid;
    private Long userReceiverid;
    @Size(max = 1000)
    private String content;
}
