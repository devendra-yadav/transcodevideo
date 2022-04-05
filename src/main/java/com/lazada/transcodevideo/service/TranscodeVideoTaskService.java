package com.lazada.transcodevideo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.exception.TaskAlreadyExistException;
import com.lazada.transcodevideo.util.STATUS;

/*
 * Service interface to interact with TranscodeTask Repository
 */
public interface TranscodeVideoTaskService {
	public void initiateTranscodingTask(MultipartFile file, String taskId) throws TaskAlreadyExistException, IOException, Exception;
	public TranscodeTask getTaskDetails(String taskUuid);
	public TranscodeTask getTaskDetailsForStatus(String taskUuid, STATUS status);
}
