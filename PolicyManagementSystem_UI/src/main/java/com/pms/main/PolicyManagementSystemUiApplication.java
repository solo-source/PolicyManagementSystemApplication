package com.pms.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pms.controller")
@EntityScan("com.pms.entity")

public class PolicyManagementSystemUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyManagementSystemUiApplication.class, args);
	}

}
