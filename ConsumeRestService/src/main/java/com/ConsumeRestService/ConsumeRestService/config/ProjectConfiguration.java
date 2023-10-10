package com.ConsumeRestService.ConsumeRestService.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor()
    {
        return new BasicAuthRequestInterceptor("admin@NitinSchool.com","admin");
    }

    @Bean
    public RestTemplate restTemplate()
    {
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        return restTemplateBuilder.basicAuthentication("admin@NitinSchool.com","admin").build();
    }
}
