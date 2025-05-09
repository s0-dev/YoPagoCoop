package com.yopagocoop.yopagocoop_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class YopagocoopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(YopagocoopBackendApplication.class, args);
	}

}
