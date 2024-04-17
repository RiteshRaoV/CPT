package com.thbs.cpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CptApplication {

	public static void main(String[] args) {
		SpringApplication.run(CptApplication.class, args);
	}

}
