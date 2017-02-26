package com.sg.springboot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sg.springboot.entity.Request;
import com.sg.springboot.entity.Response;

@RestController
@Service
public class CamundaBasic {

	@Autowired
	private RuntimeService service;
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private CaseService caseService;
	
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
			var.put("test", "TestValue");
			UUID uuid = UUID.randomUUID();
			procDef = service.startProcessInstanceByKey(processName,uuid.toString(), var).getProcessDefinitionId();
		} else {
			procDef = service.startProcessInstanceByKey(processName).getProcessDefinitionId();
		}
		
		return new ResponseEntity<String>(procDef, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/loadResources", method = RequestMethod.GET)
	public ResponseEntity<String> loadResources(){
		String deploymentId = repositoryService
				.createDeployment()
				.addClasspathResource("example.bpmn")
				.addClasspathResource("example.dmn")
				.addClasspathResource("CMMNDemo.cmmn")
				.addClasspathResource("test.bpmn")
				.deploy()
				.getId();
				
		System.out.println("Deploy resources");
		return new ResponseEntity<String>(deploymentId, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/startCMMN", method = RequestMethod.GET)
	public ResponseEntity<String> runCMMN(@RequestParam(required = true) String itemName){
		String processId= null;
		processId = caseService.createCaseInstanceByKey(itemName).getId();
		
		return new ResponseEntity<String>(processId, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/saveCamundaItem", method = RequestMethod.POST)
	public ResponseEntity<String> saveResult(@RequestParam(required = true) String itemName){
		String deploymentId = repositoryService
				.createDeployment()
				.addClasspathResource(itemName)
				.deploy()
				.getId();
		return new ResponseEntity<String>(deploymentId, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/saveResult", method = RequestMethod.POST)
	public ResponseEntity<String> saveResult(@RequestBody(required = true) Response response){
		System.out.println("Save response into the DB");
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
	
}
