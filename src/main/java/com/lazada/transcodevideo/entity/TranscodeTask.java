package com.lazada.transcodevideo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.lazada.transcodevideo.util.STATUS;

@Entity
public class TranscodeTask {

	@Id
	private String taskId;
	
	private String videoFileName;
	
	private String videoFileInputType;
	
	private String videoFileOutputType;
	
	@Enumerated(EnumType.STRING)
	private STATUS status;
	
	private String statusDetails;
	
	@Lob
	private byte[] originalVideoFileContent;
	
	@Lob
	private byte[] transcodedVideoFileContent;
	
	public TranscodeTask() {
		
	}
	
	public TranscodeTask(String taskId, String videoFileName, String videoFileInputType, byte[] originalVideoFileContent) {
		this.taskId = taskId;
		this.status=STATUS.PENDING;
		this.videoFileName = videoFileName;
		this.videoFileInputType = videoFileInputType;
		this.originalVideoFileContent = originalVideoFileContent;
		
	}

	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	
	public String getVideoFileInputType() {
		return videoFileInputType;
	}

	public void setVideoFileInputType(String videoFileInputType) {
		this.videoFileInputType = videoFileInputType;
	}


	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getStatusDetails() {
		return statusDetails;
	}

	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	public String getVideoFileOutputType() {
		return videoFileOutputType;
	}

	public void setVideoFileOutputType(String videoFileOutputType) {
		this.videoFileOutputType = videoFileOutputType;
	}

	public byte[] getOriginalVideoFileContent() {
		return originalVideoFileContent;
	}

	public void setOriginalVideoFileContent(byte[] originalVideoFileContent) {
		this.originalVideoFileContent = originalVideoFileContent;
	}

	public byte[] getTranscodedVideoFileContent() {
		return transcodedVideoFileContent;
	}

	public void setTranscodedVideoFileContent(byte[] transcodedVideoFileContent) {
		this.transcodedVideoFileContent = transcodedVideoFileContent;
	}

	
}
