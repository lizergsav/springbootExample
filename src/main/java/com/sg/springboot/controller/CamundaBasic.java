package com.sg.springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;
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

@RestController
@Service
public class CamundaBasic {

	@Autowired
	private RuntimeService service;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private HistoryService historyService;
	
	@CrossOrigin
	@RequestMapping(value = "/runProcessByKey", method = RequestMethod.POST)
	public ResponseEntity<String> runProcess(	@RequestParam(required=true) String processName, 
												@RequestBody(required=false) Request request ){
		UUID uuid = UUID.randomUUID();
		if (request != null){
			Map<String,Object> var = new HashMap<String, Object>();
			var.put("req", request);
			service.startProcessInstanceByKey(processName,uuid.toString(), var).getProcessDefinitionId();
		} else {
			System.out.println("Missing requestbody!");
			service.startProcessInstanceByKey(processName,uuid.toString()).getProcessDefinitionId();
		}
		
		return new ResponseEntity<String>(uuid.toString(), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/getTaskListForProcess", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTaskList(@RequestParam(required = false) String businessKey ){
		List<String> result = new ArrayList<String>();
		
		if (businessKey == null)
			for (Task task : this.taskService.createTaskQuery().list()){
				result.add(task.getId());
			}
		else {
			for (Task task : this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list()){
				result.add(task.getId());
			}
		}
			
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getTaskListForUser", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTaskListForUser(@RequestParam(required = true) String userId ){
		List<String> result = new ArrayList<String>();
		
		for (Task task : this.taskService.createTaskQuery().taskAssignee(userId).list()){
			result.add(task.getId());
		}
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/getClosedTaskListForUser", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getclosedTaskListForUser(@RequestParam(required = true) String userId ){
		List<String> result = new ArrayList<String>();
		
		for (HistoricTaskInstance task : this.historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).list() ){
			result.add(task.getId());
		}
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getTaskListForCandidateGroup", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTaskListForcandidateGroup(@RequestParam(required = true) String groupId ){
		List<String> result = new ArrayList<String>();
		
		for (Task task : this.taskService.createTaskQuery().taskCandidateGroup(groupId).list()){
			result.add(task.getId());
		}
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/getTaskListForCandidateUser", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTaskListForcandidateUser(@RequestParam(required = true) String userId ){
		List<String> result = new ArrayList<String>();
		
		for (Task task : this.taskService.createTaskQuery().taskCandidateUser(userId).list()){
			result.add(task.getId());
		}
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/claim", method = RequestMethod.POST)
	public ResponseEntity<String> claimTask(@RequestParam(required = true) String userId, 
											@RequestParam(required = true) String taskId){
		this.taskService.claim(taskId, userId);
			
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/unclaim", method = RequestMethod.POST)
	public ResponseEntity<String> unClaimTask(@RequestParam(required = true) String taskId){
		
		this.taskService.claim(taskId, null);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getVariables", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getVariables(@RequestParam(required = true) String taskId){
		Map<String, Object> result = new HashMap<String, Object>();
		
		result = this.taskService.getVariables(taskId);
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	/*
	@CrossOrigin
	@RequestMapping(value = "/getVariablesForClosedProcess", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getVariablesForClosedtask(@RequestParam(required = true) String taskId){
		Map<String, Object> result = new HashMap<String, Object>();
		
		for (HistoricVariableInstance task : this.historyService.createHistoricVariableInstanceQuery().taskIdIn(taskId).list()){
			result.put(task.getName(), task.getValue());
		}
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	*/
	
	@CrossOrigin
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public ResponseEntity<String> completeTask(	@RequestParam(required = true) String taskId,
												@RequestParam(required = false) Map<String, Object> variables){
		
		if (variables == null)
			this.taskService.complete(taskId);
		else
			this.taskService.complete(taskId, variables);
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delegate", method = RequestMethod.POST)
	public ResponseEntity<String> delegateTask(@RequestParam(required = true) String taskId,
												@RequestParam(required = true) String userId){
		
		this.taskService.delegateTask(taskId, userId);
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/resolve", method = RequestMethod.POST)
	public ResponseEntity<String> resolveTask(@RequestParam(required = true) String taskId,
												@RequestParam(required = false) Map<String, Object> variables){
		
		if ( variables == null)
			this.taskService.resolveTask(taskId);
		else
			this.taskService.resolveTask(taskId, variables);
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value="/startCMMN", method = RequestMethod.GET)
	public ResponseEntity<String> runCMMN(@RequestParam(required = true) String itemName){
		String processId= null;
		processId = caseService.createCaseInstanceByKey(itemName).getId();
		
		return new ResponseEntity<String>(processId, HttpStatus.OK);
	}
		
}
