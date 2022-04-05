package com.lazada.transcodevideo.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class TaskExecutorConfig {

	
	@Value("${task.executor.core.pool.size}")
	private int taskExecutorCorePoolSize;
	
	@Value("${task.executor.max.pool.size}")
	private int taskExecutorMaxPoolSize;
	
	@Value("${task.executor.queue.capacity}")
	private int taskExecutorQueueCapacity;
	
	
	@Bean
	@Primary
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(taskExecutorCorePoolSize);
		taskExecutor.setMaxPoolSize(taskExecutorMaxPoolSize);
		taskExecutor.setQueueCapacity(taskExecutorQueueCapacity);
		taskExecutor.setThreadNamePrefix("TranscodeVideo-");
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAllowCoreThreadTimeOut(true);
		taskExecutor.initialize();
		return taskExecutor;
	}
	
}
