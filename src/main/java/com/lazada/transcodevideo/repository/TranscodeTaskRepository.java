package com.lazada.transcodevideo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lazada.transcodevideo.entity.TranscodeTask;
import com.lazada.transcodevideo.util.STATUS;


public interface TranscodeTaskRepository extends JpaRepository<TranscodeTask, String>{
	public List<TranscodeTask> findByStatus(STATUS status);
}
