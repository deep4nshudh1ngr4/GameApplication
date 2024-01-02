package com.simpleViralGames;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.simpleViralGames.project.Constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@ComponentScan(basePackages = "com.SimpleViralGames.project")
@RestController
@EnableSwagger2
public class GameProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameProjectApplication.class, args);
	}

	@Bean
	public MongoClient mongoClient() {
		String connectionString = Constants.connectionString;
		return MongoClients.create(connectionString);
	}
}
