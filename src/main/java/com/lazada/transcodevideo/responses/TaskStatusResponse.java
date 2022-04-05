package com.lazada.transcodevideo.responses;

import org.springframework.stereotype.Component;

@Component
public class TaskStatusResponse {
	
	private String status;
	private String failureReason;
	
	public TaskStatusResponse() {
		// TODO Auto-generated constructor stub
	}

	public TaskStatusResponse(String status, String failureReason) {
		
		this.status = status;
		this.failureReason = failureReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	
	
}
