package com.steampals.steampals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.steampals.steampals.model")
public class SteampalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteampalsApplication.class, args);
	}

}
