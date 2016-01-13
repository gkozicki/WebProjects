package com.quartz.scheduling;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestScheduler {
	
	public static void main(String[] args) throws Exception{
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity("JobName","groupName").build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("triggerName", "triggerGroup")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job,trigger);
	}

}
