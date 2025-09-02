package com.example.SadadApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SADAD APIS", version = "1.0", description = "Managing SADAD APIS"))
public class  SadadApiApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(SadadApiApplication.class, args);
	}

}
