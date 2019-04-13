package com.abhee.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
	private static final Logger logger = LogManager.getLogger(BatchController.class);
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@GetMapping(value = { "/load" })
	public BatchStatus launchJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String,JobParameter> jobParameter = new HashMap<>();
		jobParameter.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameter = new JobParameters(jobParameter);
		JobExecution jobExecution = jobLauncher.run(job, parameter);
		logger.info("Job is running ...");
		while(jobExecution.isRunning()) {
			logger.info("...");
		}		
		return jobExecution.getStatus();
		
	}

}
