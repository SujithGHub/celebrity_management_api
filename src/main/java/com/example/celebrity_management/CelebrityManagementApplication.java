package com.example.celebrity_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CelebrityManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelebrityManagementApplication.class, args);
	}

}
