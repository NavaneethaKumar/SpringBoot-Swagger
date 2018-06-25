package com.coffeeshop.domain;

import java.util.List;

public class OperationOutcome {
	
	public OperationOutcome(final String status,final String error,final List<String> userMessage)
	{
		this.status=status;
		this.error=error;
		this.userMessage=userMessage;
	}
	
	private String status;
	private String error;
	private List<String> userMessage;
	public String getStatus() {
		return status;
	}
	public String getError() {
		return error;
	}
	
	public List<String> getUserMessage() {
		return userMessage;
	}
	

}
