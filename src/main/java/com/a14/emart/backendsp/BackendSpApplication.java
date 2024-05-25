package com.a14.emart.backendsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendSpApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
		System.setProperty("JWT_EXPIRE_DURATION", dotenv.get("JWT_EXPIRE_DURATION"));

		SpringApplication.run(BackendSpApplication.class, args);
	}

}



