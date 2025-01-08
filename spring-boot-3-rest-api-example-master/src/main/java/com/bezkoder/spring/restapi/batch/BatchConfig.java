package com.bezkoder.spring.restapi.batch;

import com.bezkoder.spring.restapi.batch.tasklet.ExcelWriterTasklet;
import com.bezkoder.spring.restapi.batch.tasklet.TestTasklet;
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
    private final ExcelWriterTasklet excelWriterTasklet;
    private final TestTasklet testTasklet;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       ExcelWriterTasklet excelWriterTasklet,
                       TestTasklet testTasklet
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.excelWriterTasklet = excelWriterTasklet;
        this.testTasklet = testTasklet;
    }

    @Bean
    public Step excelWriterStep() {
        logger.info("excelWriterStep");
        return stepBuilderFactory.get("excelWriterStep")
                .tasklet(excelWriterTasklet)
                .build();
    }

    @Bean
    public Job excelWriterJob(Step excelWriterStep) {
        logger.info("excelWriterJob");
        return jobBuilderFactory.get("excelWriterJob")
                .start(excelWriterStep)
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