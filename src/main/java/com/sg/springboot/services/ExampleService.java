package com.sg.springboot.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ExampleService implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		
		execution.setVariable("result", "zero");
		
	}

}
