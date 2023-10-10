package com.ConsumeRestService.ConsumeRestService.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
public class ProjectConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor()
    {
        return new BasicAuthRequestInterceptor("admin@NitinSchool.com","admin");
    }
}
