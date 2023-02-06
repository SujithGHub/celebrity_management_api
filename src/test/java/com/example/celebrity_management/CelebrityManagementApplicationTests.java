package com.example.celebrity_management;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @SpringBootTest
class CelebrityManagementApplicationTests {

	@Test
	void contextLoads() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("9566824926"));
	}

}
