package com.lazada.transcodevideo.exception;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Log logger=LogFactory.getLog("GlobalExceptionHandler");
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException(IOException ex) {
		logger.error("Some issue while reading file data. "+ex.getMessage());
		
		ResponseEntity<String> response=ResponseEntity
				.badRequest()
				.header("description", "Invalid input (invalid taskUuid or video).")
				.body("Some issue while reading file data. "+ex.getMessage());
		
		return response;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		logger.error("Some issue while initiating trancode video task. "+ex.getMessage());
		ResponseEntity<String> response=ResponseEntity
				.badRequest()
				.header("description", "Invalid input (invalid taskUuid or video).")
				.body("Some issue while initiating trancode video task. "+ex.getMessage());
				
		return response;
	}
	
	@ExceptionHandler(TaskAlreadyExistException.class)
	public ResponseEntity<String> handleTaskAlreadyExistException(TaskAlreadyExistException ex) {
		logger.warn("This task already exists. "+ex.getMessage());
		ResponseEntity<String> response=ResponseEntity
				.badRequest()
				.header("description", "Invalid input (invalid taskUuid or video).")
				.body(ex.getMessage());
				
		return response;
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
		logger.warn("Invalid TaskUUID. "+ex.getMessage());
		ResponseEntity<String> response=ResponseEntity
				.badRequest()
				.header("description", "Invalid input (invalid taskUuid).")
				.body(ex.getMessage());
				
		return response;
	}
	
	@ExceptionHandler(UnknownTaskUUIDException.class)
	public ResponseEntity<String> handleUnknownTaskUUIDException(UnknownTaskUUIDException ex) {
		logger.warn("Unknown taskUuid. "+ex.getMessage());
		
		ResponseEntity<String> response=ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.header("description", "Unknow taskUuid")
				.body(ex.getMessage());
		
		return response;
	}
	
	@ExceptionHandler(TaskNotCompletedException.class)
	public ResponseEntity<String> handleUnknownTaskUUIDException(TaskNotCompletedException ex) {
		logger.warn("Task Not completed. "+ex.getMessage());
		ResponseEntity<String> response=ResponseEntity
				.badRequest()
				.body(ex.getMessage());
				
		return response;
	}
}
