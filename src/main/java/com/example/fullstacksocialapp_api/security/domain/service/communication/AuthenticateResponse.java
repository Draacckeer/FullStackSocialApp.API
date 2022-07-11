package com.example.fullstacksocialapp_api.security.domain.service.communication;

import com.example.fullstacksocialapp_api.security.resources.AuthenticateResource;
import com.example.fullstacksocialapp_api.shared.domain.service.communication.BaseResponse;

public class AuthenticateResponse extends BaseResponse<AuthenticateResource> {

    public AuthenticateResponse(String message){
        super(message);
    }

    public AuthenticateResponse(AuthenticateResource resource){
        super(resource);
    }
}