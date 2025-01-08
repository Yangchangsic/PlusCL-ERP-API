package com.bezkoder.spring.restapi.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    private final JobLauncher jobLauncher;
    private final Job excelWriterJob;
    private final Job testJob;

    @Autowired
    public SchedulerConfig(JobLauncher jobLauncher,
                           Job excelWriterJob,
                           Job testJob
    ) {
        this.jobLauncher = jobLauncher;
        this.excelWriterJob = excelWriterJob;
        this.testJob = testJob;
    }

    //@Scheduled(cron = "0 0 10 * * *") 10시
    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runExcelJob() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("runExcelJob");
        logger.info("runExcelJob");
        jobLauncher.run(excelWriterJob, jobParameters);
    }

    //@Scheduled(cron = "0/2 * * * * *")//2초마다
    public void runTestJob() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("runTestJob");
        logger.info("runTestJob");
        jobLauncher.run(testJob, jobParameters);
    }
}