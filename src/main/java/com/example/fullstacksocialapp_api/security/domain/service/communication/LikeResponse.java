package com.example.fullstacksocialapp_api.security.domain.service.communication;

import com.example.fullstacksocialapp_api.security.resources.UserLikeResource;
import com.example.fullstacksocialapp_api.shared.domain.service.communication.BaseResponse;

public class LikeResponse extends BaseResponse<UserLikeResource> {

    public LikeResponse(String message) {
        super(message);
    }

    public LikeResponse(UserLikeResource resource) {
        super(resource);
    }
}
}
