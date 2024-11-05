package com.margulan.uniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UniprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniprojectApplication.class, args);
	}

}
