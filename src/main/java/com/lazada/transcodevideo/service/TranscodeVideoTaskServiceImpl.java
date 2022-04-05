package com.lazada.transcodevideo.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.exception.InvalidInputException;
import com.lazada.transcodevideo.exception.TaskAlreadyExistException;
import com.lazada.transcodevideo.exception.TaskNotCompletedException;
import com.lazada.transcodevideo.exception.UnknownTaskUUIDException;
import com.lazada.transcodevideo.repository.TranscodeTaskRepository;
import com.lazada.transcodevideo.util.STATUS;

@Service
public class TranscodeVideoTaskServiceImpl implements TranscodeVideoTaskService {

	@Autowired
	private TranscodeTaskRepository transcodeTaskRepo;
	
	/*
	 * save the incoming transcode video request TRANSCODE_TASK entity to data source.
	 */
	@Override
	public void initiateTranscodingTask(MultipartFile file, String taskUuid) throws IOException {
		
		if(taskUuid==null||taskUuid.trim().length()==0) {
			throw new InvalidInputException("taskUuid cant be null or empty");
		}else {
			try {
				UUID.fromString(taskUuid);
			}catch (Exception e) {
				throw new InvalidInputException("Invalid taskUuid: "+taskUuid);
			}
		}
		
		if(file==null||file.getSize()==0) {
			throw new InvalidInputException("Input file cant be null or blank");
		}
		
		TranscodeTask task = new TranscodeTask(taskUuid, file.getOriginalFilename(), file.getContentType(), file.getBytes());
		
		Optional<TranscodeTask> fetchedTask=transcodeTaskRepo.findById(taskUuid);
		
		if(fetchedTask.isEmpty()) {
			transcodeTaskRepo.save(task);
		}else {
			throw new TaskAlreadyExistException("A task with this taskUuid ("+taskUuid+") already exists.");
		}
		
	}

	/*
	 * Returns the TRANSCODE_TASK details from data source for the given taskUUID
	 */
	@Override
	public TranscodeTask getTaskDetails(String taskUuid) {
		
		if(taskUuid==null||taskUuid.trim().length()==0) {
			throw new InvalidInputException("taskUuid cant be null or empty");
		}else {
			try {
				UUID.fromString(taskUuid);
			}catch (Exception e) {
				throw new InvalidInputException("Invalid taskUuid: "+taskUuid);
			}
		}
		
		Optional<TranscodeTask> fetchedTask=transcodeTaskRepo.findById(taskUuid);
		
		if(fetchedTask.isEmpty()) {
			throw new UnknownTaskUUIDException("Unknown taskUuid. "+taskUuid);
		}
		
		return fetchedTask.get();
	}

	/*
	 * Returns TRANSCODE_TASK entity if the status of the input taskUUID matches the input status
	 */
	@Override
	public TranscodeTask getTaskDetailsForStatus(String taskUuid, STATUS status) {
		if(taskUuid==null||taskUuid.trim().length()==0) {
			throw new InvalidInputException("taskUuid cant be null or empty");
		}else {
			try {
				UUID.fromString(taskUuid);
			}catch (Exception e) {
				throw new InvalidInputException("Invalid taskUuid: "+taskUuid);
			}
		}
		
		Optional<TranscodeTask> fetchedTask=transcodeTaskRepo.findById(taskUuid);
		
		if(fetchedTask.isEmpty()) {
			throw new UnknownTaskUUIDException("Unknown taskUuid. "+taskUuid);
		}else if(fetchedTask.get().getStatus()!=status) {
			throw new TaskNotCompletedException("TaskId "+taskUuid+" is not yet completed. Current status is "+fetchedTask.get().getStatus());
		}
		
		return fetchedTask.get();
	}
	
}
