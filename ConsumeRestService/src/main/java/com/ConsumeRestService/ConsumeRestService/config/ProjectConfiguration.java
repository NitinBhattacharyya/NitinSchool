package com.ConsumeRestService.ConsumeRestService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
public class ProjectConfiguration {
    @Bean
    public BasicAuthenticationInterceptor basicAuthenticationInterceptor()
    {
        return new BasicAuthenticationInterceptor("admin@NitinSchool.com","admin");
    }
}
