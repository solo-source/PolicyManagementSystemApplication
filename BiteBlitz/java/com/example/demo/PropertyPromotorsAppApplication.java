package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.controller.PropertyController;
import com.entities.PropertyInfo;
import com.entities.Agency;


@EntityScan("com.entities")
@ComponentScan(basePackages={"com.repository","com.dao","com.controller","com.entities","com.service","com.exception"})
@EnableJpaRepositories("com.repository")
@SpringBootApplication
public class PropertyPromotorsAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(PropertyPromotorsAppApplication.class, args);

	}

}
	 	  	      	 		     	     	        	 	
