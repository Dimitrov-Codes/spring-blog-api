package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Spring Boot Blog REST API",
		description = "Blog App API Documentation",
		version = "v1.0",
		contact = @Contact(
			name = "Adnan",
			email = "messageadnan1@gmail.com"
		)
//		license = @License(<name>, <url>)
	),
	externalDocs = @ExternalDocumentation(
		description = "Spring Boot Blog App Documentation",
		url = "https://github.com/Dimitrov-Codes/spring-blog-api"
	)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
