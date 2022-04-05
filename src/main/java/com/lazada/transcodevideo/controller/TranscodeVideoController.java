package com.lazada.transcodevideo.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.exception.TaskAlreadyExistException;
import com.lazada.transcodevideo.responses.TaskStatusResponse;
import com.lazada.transcodevideo.service.TranscodeVideoTaskService;
import com.lazada.transcodevideo.util.STATUS;
import com.lazada.transcodevideo.util.Utilities;

@RestController
@RequestMapping("transcode-task")
public class TranscodeVideoController {

	private Log logger=LogFactory.getLog("TranscodeVideoController");
	
	@Autowired
	private TranscodeVideoTaskService transcodeVideoService;
	
	@Autowired
	private TaskStatusResponse taskStatusResponse;
	
	/*
	 * Rest endpoint to initiate the video transcoding
	 */
	@PostMapping("/init")
	public ResponseEntity<String> initTask(@RequestParam("video") MultipartFile video, @RequestParam("taskUuid") String taskUuid) throws TaskAlreadyExistException, IOException, Exception{
		logger.info("TaskUUID : "+taskUuid+"Original filename : "+video.getOriginalFilename()+"Fize size : "+Utilities.bytesToMB(video.getSize())+" MB");
		transcodeVideoService.initiateTranscodingTask(video, taskUuid);
		logger.info("Trancode video Task UUID successfully created : "+taskUuid);
		return new ResponseEntity<String>("The transcoding request has been sucessfully initiated.", HttpStatus.OK);
	}
	
	/*
	 * Rest endpoint to get the status of the given taskUUID
	 */
	@GetMapping("/{taskUuid}/status")
	public ResponseEntity<TaskStatusResponse> getTaskStatus(@PathVariable("taskUuid") String taskUuid){
		logger.info("Getting status for taskuuid "+taskUuid);
		
		TranscodeTask task=transcodeVideoService.getTaskDetails(taskUuid);
		taskStatusResponse.setStatus(task.getStatus().toString());
		logger.info("Current status for taskuuid : "+task.getStatus().toString());
		if(task.getStatus()==STATUS.FAILED) {
			taskStatusResponse.setFailureReason(task.getStatusDetails());
		}
		
		ResponseEntity<TaskStatusResponse> response=ResponseEntity
				.ok()
				.header("description", "Successfully returned status")
				.body(taskStatusResponse);
		
		return response;
	}
	
	
	/*
	 * Rest endpoint to download the transcoded video if the transcoding is successfully completed
	 */
	@GetMapping("/{taskUuid}/transcoded-video")
	public ResponseEntity<Resource> downloadVideo(@PathVariable("taskUuid") String taskUuid){
		
		TranscodeTask task=transcodeVideoService.getTaskDetailsForStatus(taskUuid, STATUS.COMPLETED);
		
		ResponseEntity<Resource> resource=ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(task.getVideoFileOutputType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attatchment: filename=transcoded_"+task.getVideoFileName())
				.header(HttpHeaders.CONTENT_TYPE, task.getVideoFileOutputType())
				.body(new ByteArrayResource(task.getTranscodedVideoFileContent()));
				
			
		return resource;
	}
}
