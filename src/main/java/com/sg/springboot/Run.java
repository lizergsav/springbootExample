package com.sg.springboot;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ProcessApplication
public class Run extends SpringBootProcessApplication {
	
	@Autowired
	private CamundaProcess process;
	
	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
		
		CamundaProcess process = new CamundaProcess();
		
		System.out.println(process.runProcess("test"));
		
	}

}
