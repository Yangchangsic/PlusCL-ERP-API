package com.bezkoder.spring.restapi.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public SchedulerConfig(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(cron = "0/6 * * * * *")
    public void runJob() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        System.out.println("runJob");
        jobLauncher.run(job, jobParameters);
    }
}