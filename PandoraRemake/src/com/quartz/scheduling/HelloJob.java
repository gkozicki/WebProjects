package com.quartz.scheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException{
		System.out.println("Quartz scheduler job: hello");
	}

}
