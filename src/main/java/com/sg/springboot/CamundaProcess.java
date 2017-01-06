package com.sg.springboot;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamundaProcess {
	
	@Autowired
	private RuntimeService runtimeService;

	public String runProcess(String name){
		return this.runtimeService.startProcessInstanceByKey(name).getProcessInstanceId();
	}
}
