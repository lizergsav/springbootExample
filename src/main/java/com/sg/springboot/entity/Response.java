package com.sg.springboot.entity;

import java.io.Serializable;

public class Response implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3042479477590366687L;
	private String response;
	private String message;
	public final String getResponse() {
		return response;
	}
	public final void setResponse(String response) {
		this.response = response;
	}
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
	

}
