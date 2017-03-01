package com.sg.springboot.config;

import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Camunda {

	@Bean
	public ConnectProcessEnginePlugin connectProcessEnginePlugin() {
	  return new ConnectProcessEnginePlugin();
	}
	
	/*
	@Bean
	@Autowired
	public SpringProcessEngineConfiguration processEngineConfiguration(){
		return new SpringProcessEngineConfiguration();
	}
	
	@Bean
	@Autowired
	public ProcessEngineFactoryBean processEngine(){
		return new ProcessEngineFactoryBean();
	}
	
	*/
}
