package com.lazada.transcodevideo.task;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.repository.TranscodeTaskRepository;
import com.lazada.transcodevideo.service.TranscodeVideoService;
import com.lazada.transcodevideo.util.STATUS;

@Component
public class TranscodeTasks {
	
	private Log logger=LogFactory.getLog("TranscodeTasks");
	
	@Autowired
	private TranscodeVideoService transcodeVideoService;
	
	@Autowired
	private TranscodeTaskRepository transcodeTaskRepo;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Value("${transcode.video.output.mime.type}")
	private String videoFileOutputType;
	
	/*
	 * Scheduled method that will run at regular intervals as defined
	 * this method fetches all the tasks in PENDING status and start a thread to start transcoding the 
	 * corresponding video
	 */
	@Async
	@Scheduled(initialDelayString = "${transcode.task.initial.delay}", fixedDelayString = "${transcode.task.fixed.delay}")
	public void startTranscoding() {
		logger.info("Transcoding of pending tasks will be picked up");
		List<TranscodeTask> pendingTasks=transcodeTaskRepo.findByStatus(STATUS.PENDING);
		logger.info("Total Pending tasks : "+pendingTasks.size());
		CountDownLatch countDownLatch=new CountDownLatch(pendingTasks.size());
		for(TranscodeTask task:pendingTasks) {
			taskExecutor.execute(new TranscodeVideo(task,transcodeTaskRepo, transcodeVideoService, videoFileOutputType, countDownLatch));
		}
		
	}
}
