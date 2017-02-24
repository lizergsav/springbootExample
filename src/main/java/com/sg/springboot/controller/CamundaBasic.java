package com.sg.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sg.springboot.entity.Request;
import com.sg.springboot.entity.Response;

@RestController
public class CamundaBasic {

	@Autowired
	private RuntimeService service;
	
	@CrossOrigin
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<String> test(){
		
		return new ResponseEntity<String>("Running", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/runProcessByKey", method = RequestMethod.POST)
	public ResponseEntity<String> runProcess(	@RequestParam(required=true) String processName, 
												@RequestBody(required=false) Request request ){
		String procDef = null;
		if (request != null){
			Map<String,Object> var = new HashMap<String, Object>();
			var.put("req", request);
			procDef = service.startProcessInstanceByKey(processName, var).getProcessDefinitionId();
		} else {
			procDef = service.startProcessInstanceByKey(processName).getProcessDefinitionId();
		}
		
		return new ResponseEntity<String>(procDef, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/saveResult", method = RequestMethod.POST)
	public ResponseEntity<String> saveResult(@RequestBody(required = true) Response response){
		System.out.println("Save response into the DB");
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
	
}
