package com.paazl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringWebservicesTestCaseApplication {

    public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(SpringWebservicesTestCaseApplication.class, args);
	}
}
