package com.codestates.edusync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EdusyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdusyncApplication.class, args);
	}

}
