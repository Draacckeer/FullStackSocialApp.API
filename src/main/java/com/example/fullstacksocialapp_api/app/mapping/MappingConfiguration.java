package com.example.fullstacksocialapp_api.app.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration("SocialAppApiMappingConfiguration")
public class MappingConfiguration implements Serializable {
    @Bean
    public PublicationMapper publicationMapper() { return new PublicationMapper(); }
    @Bean
    public PublicationCommentMapper publicationMessageMapper() { return new PublicationCommentMapper(); }
}
