package com.bezkoder.spring.restapi.batch;

import com.bezkoder.spring.restapi.batch.tasklet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CurLabelTasklet curLabelTasklet;
    private final EstherFomularrTasklet estherFomularrTasklet;
    private final SetProductTasklet setProductTasklet;
    private final SetHomeProductTasklet setHomeProductTasklet;
    private final TestTasklet testTasklet;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       CurLabelTasklet curLabelTasklet,
                       EstherFomularrTasklet estherFomularrTasklet,
                       SetProductTasklet setProductTasklet,
                       SetHomeProductTasklet setHomeProductTasklet,
                       TestTasklet testTasklet
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.curLabelTasklet = curLabelTasklet;
        this.estherFomularrTasklet = estherFomularrTasklet;
        this.setProductTasklet = setProductTasklet;
        this.setHomeProductTasklet = setHomeProductTasklet;
        this.testTasklet = testTasklet;
    }

    //curLabel
    @Bean
    public Job curLabelJob(Step curLabelStep) {
        logger.info("curLabelJob");
        return jobBuilderFactory.get("curLabelJob")
                .start(curLabelStep)
                .build();
    }

    @Bean
    public Step curLabelStep() {
        logger.info("curLabelStep");
        return stepBuilderFactory.get("curLabelStep")
                .tasklet(curLabelTasklet)
                .build();
    }

    //estherFomular
    @Bean
    public Job estherFomularJob(Step estherFomularStep) {
        logger.info("estherFomularJob");
        return jobBuilderFactory.get("estherFomularJob")
                .start(estherFomularStep)
                .build();
    }

    @Bean
    public Step estherFomularStep() {
        logger.info("estherFomularStep");
        return stepBuilderFactory.get("estherFomularStep")
                .tasklet(estherFomularrTasklet)
                .build();
    }

    //setProduct
    @Bean
    public Job setProductJob(Step setProductStep) {
        logger.info("setProductJob");
        return jobBuilderFactory.get("setProductJob")
                .start(setProductStep)
                .build();
    }

    @Bean
    public Step setProductStep() {
        logger.info("setProductStep");
        return stepBuilderFactory.get("setProductStep")
                .tasklet(setProductTasklet)
                .build();
    }

    //setProduct
    @Bean
    public Job setHomeProductJob(Step setHomeProductStep) {
        logger.info("setHomeProductJob");
        return jobBuilderFactory.get("setHomeProductJob")
                .start(setHomeProductStep)
                .build();
    }

    @Bean
    public Step setHomeProductStep() {
        logger.info("setHomeProductStep");
        return stepBuilderFactory.get("setHomeProductStep")
                .tasklet(setHomeProductTasklet)
                .build();
    }

    @Bean
    public Step testStep() {
        logger.info("testStep");
        return stepBuilderFactory.get("testStep")
                .tasklet(testTasklet)
                .build();
    }

    @Bean
    public Job testJob(Step testStep) {
        logger.info("testJob");
        return jobBuilderFactory.get("testJob")
                .start(testStep)
                .build();
    }
}