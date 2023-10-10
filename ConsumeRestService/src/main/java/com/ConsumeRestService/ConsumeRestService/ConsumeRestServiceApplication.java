package com.ConsumeRestService.ConsumeRestService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ConsumeRestService.ConsumeRestService.proxy")
public class ConsumeRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumeRestServiceApplication.class, args);
	}

}
