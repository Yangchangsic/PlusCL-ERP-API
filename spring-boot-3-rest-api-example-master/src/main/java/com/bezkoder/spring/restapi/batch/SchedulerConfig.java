package com.bezkoder.spring.restapi.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public SchedulerConfig(JobLauncher jobLauncher, Job helloWorldJob) {
        this.jobLauncher = jobLauncher;
        this.job = helloWorldJob;
    }

    @Scheduled(cron = "0 * * * * *")
    public void runJob() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("currentTime", new Date()) // 현재 시간을 파라미터로 추가
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}