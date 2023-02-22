package com.personal.homework_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomeworkSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkSecurityApplication.class, args);
	}

}
