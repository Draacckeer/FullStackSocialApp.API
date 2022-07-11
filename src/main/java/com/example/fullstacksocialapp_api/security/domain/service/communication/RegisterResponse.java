package com.example.fullstacksocialapp_api.security.domain.service.communication;

import com.example.fullstacksocialapp_api.security.resources.UserResource;
import com.example.fullstacksocialapp_api.shared.domain.service.communication.BaseResponse;

public class RegisterResponse extends BaseResponse<UserResource> {

    public RegisterResponse(String message){
        super(message);
    }

    public RegisterResponse(UserResource resource){
        super(resource);
    }
}
