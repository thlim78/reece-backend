package com.reece.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.reece"})
public class ReeceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReeceBackendApplication.class, args);
	}
}
