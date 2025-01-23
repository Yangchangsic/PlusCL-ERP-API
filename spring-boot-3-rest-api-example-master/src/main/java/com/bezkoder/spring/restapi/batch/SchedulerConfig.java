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
    private final Job curLabelJob;
    private final Job estherFomularJob;
    private final Job setProductJob;
    private final Job setHomeProductJob;
    private final Job quSetHomeProductJob;

    private final Job testJob;

    @Autowired
    public SchedulerConfig(JobLauncher jobLauncher,
                           Job curLabelJob,
                           Job estherFomularJob,
                           Job setProductJob,
                           Job setHomeProductJob,
                           Job quSetHomeProductJob,
                           Job testJob
    ) {
        this.jobLauncher = jobLauncher;
        this.curLabelJob = curLabelJob;
        this.estherFomularJob = estherFomularJob;
        this.setProductJob = setProductJob;
        this.setHomeProductJob = setHomeProductJob;
        this.quSetHomeProductJob = quSetHomeProductJob;
        this.testJob = testJob;
    }


    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runCurLabel() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("runExcelJob");
        logger.info("runExcelJob");
        jobLauncher.run(curLabelJob, jobParameters);
    }

    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runEstherFomular() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("runExcelJob");
        logger.info("runExcelJob");
        jobLauncher.run(estherFomularJob, jobParameters);
    }

    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runSetProduct() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("runExcelJob");
        logger.info("runExcelJob");
        jobLauncher.run(setProductJob, jobParameters);
    }


    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runSetHomeProduct() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("setHomeProductJob");
        logger.info("setHomeProductJob");
        jobLauncher.run(setHomeProductJob, jobParameters);
    }

    @Scheduled(cron = "0 0 10 * * *")//매 시각 45분마다
    public void runQuSetHomeProduct() throws Exception {
        // 매 실행마다 유니크한 JobParameters 생성
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 매 실행마다 유니크한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currentTime", formattedDate) // yyyyMMdd 형식의 날짜 추가
                .toJobParameters();

        logger.debug("quSetHomeProductJob");
        logger.info("quSetHomeProductJob");
        jobLauncher.run(quSetHomeProductJob, jobParameters);
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