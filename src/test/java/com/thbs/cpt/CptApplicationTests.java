package com.thbs.cpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest(classes = CptApplication.class)
class CptApplicationTests {

	@Autowired
	private CptApplication application;

	@Test
	void contextLoads() {
		assertNotNull(application);
	}
	
}
