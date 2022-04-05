package com.lazada.transcodevideo;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.repository.TranscodeTaskRepository;
import com.lazada.transcodevideo.util.STATUS;

@SpringBootTest
@AutoConfigureMockMvc
public class TranscodeVideoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TranscodeTaskRepository transcodeTaskRepository;
	
	TranscodeTask task1=new TranscodeTask("123b38d9-1111-4a30-a82e-456d2eaaebfa", "nature.mp4", "video/mp4", "video_data".getBytes());
	
	@Test
	public void getTaskStatus_success() throws Exception {
		task1.setStatus(STATUS.PENDING);
		Mockito.when(transcodeTaskRepository.findById("123b38d9-1111-4a30-a82e-456d2eaaebfa")).thenReturn(Optional.of(task1));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/transcode-task/123b38d9-1111-4a30-a82e-456d2eaaebfa/status")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.is("PENDING")));
	}
	
	@Test
	public void getTaskStatus_failure_badRequest() throws Exception {
		task1.setStatus(STATUS.PENDING);
		Mockito.when(transcodeTaskRepository.findById("123b38d9-1111-4a30-a82e-456d2eaaebfa")).thenReturn(Optional.of(task1));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/transcode-task/123b38d9/status")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void getTaskStatus_failure_notFound() throws Exception {
		task1.setStatus(STATUS.PENDING);
		Mockito.when(transcodeTaskRepository.findById("123b38d9-1111-4a30-a82e-456d2eaaebfa")).thenReturn(Optional.of(task1));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/transcode-task/123b38d9-2222-4a30-a82e-456d2eaaebfa/status")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
}
