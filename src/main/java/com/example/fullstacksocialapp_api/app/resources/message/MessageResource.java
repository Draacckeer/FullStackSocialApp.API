package com.example.fullstacksocialapp_api.app.resources.message;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class MessageResource {
    private Long id;
    private Long userSenderid;
    private Long userReceiverid;
    private String content;
    private String createdAt;
    private String updatedAt;
}
