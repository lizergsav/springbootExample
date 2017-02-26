package com.sg.springboot.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ShowVariables implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("The variables".concat(execution.getVariables().toString()));
		
	}

}
