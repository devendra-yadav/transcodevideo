package com.lazada.transcodevideo.exception;

public class TaskAlreadyExistException extends RuntimeException {
	
	public TaskAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
}
