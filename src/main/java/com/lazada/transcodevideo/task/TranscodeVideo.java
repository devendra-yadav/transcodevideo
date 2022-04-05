package com.lazada.transcodevideo.task;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.repository.TranscodeTaskRepository;
import com.lazada.transcodevideo.service.TranscodeVideoService;
import com.lazada.transcodevideo.util.STATUS;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;

/*
 * This is the runnable class for video transcoding tasks that will run in parallel.
 */
public class TranscodeVideo implements Runnable{
	
	private Log logger=LogFactory.getLog("TranscodeVideo");
	
	private TranscodeVideoService transcodeVideoService;
	
	private TranscodeTaskRepository transcodeTaskRepo;
	
	private TranscodeTask task;
	
	private String videoFileOutputType;
	
	private CountDownLatch countDownLatch;
	
	public TranscodeVideo(TranscodeTask task, TranscodeTaskRepository transcodeTaskRepo, TranscodeVideoService transcodeVideoService,String videoFileOutputType, CountDownLatch countDownLatch) {
		this.task=task;
		this.transcodeTaskRepo=transcodeTaskRepo;
		this.transcodeVideoService=transcodeVideoService;
		this.videoFileOutputType=videoFileOutputType;
		this.countDownLatch=countDownLatch;
	}
	
	@Override
	public void run() {
		logger.info("Picked up the task to transcode taskID "+task.getTaskId());
		task.setStatus(STATUS.TRANSCODING);
		transcodeTaskRepo.saveAndFlush(task);
		
		try {
			byte[] transcodedVideo=transcodeVideoService.trancodeVideo(task.getOriginalVideoFileContent(), task.getVideoFileName());
			task.setTranscodedVideoFileContent(transcodedVideo);
			task.setVideoFileOutputType(videoFileOutputType);
		} catch (IllegalArgumentException e) {
			task.setStatus(STATUS.FAILED);
			task.setStatusDetails("Failed to encode. IllegalArgumentException : "+e.getMessage());
			transcodeTaskRepo.save(task);
			e.printStackTrace();
		} catch (InputFormatException e) {
			task.setStatus(STATUS.FAILED);
			task.setStatusDetails("Failed to encode. InputFormatException : "+e.getMessage());
			transcodeTaskRepo.save(task);
			e.printStackTrace();
		} catch (IOException e) {
			task.setStatus(STATUS.FAILED);
			task.setStatusDetails("Failed to encode. IOException : "+e.getMessage());
			transcodeTaskRepo.save(task);
			e.printStackTrace();
		} catch (EncoderException e) {
			task.setStatus(STATUS.FAILED);
			task.setStatusDetails("Failed to encode. EncoderException : "+e.getMessage());
			transcodeTaskRepo.save(task);
			e.printStackTrace();
		}catch (Exception e) {
			task.setStatus(STATUS.FAILED);
			task.setStatusDetails("Failed to encode. Exception : "+e.getMessage());
			transcodeTaskRepo.save(task);
		}finally {
			countDownLatch.countDown();
		}
		
		if(task.getStatus()==STATUS.TRANSCODING) {
			task.setStatus(STATUS.COMPLETED);
			transcodeTaskRepo.save(task);
			logger.info("Transcode task completed for taskID "+task.getTaskId());
		}else {
			logger.error("Failed to transcode taskID "+task.getTaskId());
		}
		transcodeTaskRepo.flush();
	}
	
	public TranscodeTask getTask() {
		return task;
	}

	public void setTask(TranscodeTask task) {
		this.task = task;
	}

}
