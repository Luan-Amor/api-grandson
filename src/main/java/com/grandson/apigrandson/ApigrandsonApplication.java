package com.grandson.apigrandson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
@EnableScheduling
public class ApigrandsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigrandsonApplication.class, args);
	}

}
