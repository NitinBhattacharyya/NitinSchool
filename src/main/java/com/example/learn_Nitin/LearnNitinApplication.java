package com.example.learn_Nitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.learn_Nitin.repository")
@EntityScan("com.example.learn_Nitin.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class LearnNitinApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnNitinApplication.class, args);
	}

}
