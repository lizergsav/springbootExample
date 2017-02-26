package com.sg.springboot;

import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages={"com.sg.springboot"})
@EnableSwagger2
public class Run extends SpringBootProcessApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
				
	}

}