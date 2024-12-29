package com.bezkoder.spring.restapi.batch;

import com.bezkoder.spring.restapi.service.ApiService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExcelWriterTasklet excelWriterTasklet;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ExcelWriterTasklet excelWriterTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.excelWriterTasklet = excelWriterTasklet;
    }

    @Bean
    public Step excelWriterStep() {
        System.out.println("excelWriterStep");


        return stepBuilderFactory.get("excelWriterStep")
                .tasklet(excelWriterTasklet)
                .build();
    }

    @Bean
    public Job excelWriterJob(Step excelWriterStep) {
        System.out.println("excelWriterJob");
        return jobBuilderFactory.get("excelWriterJob")
                .start(excelWriterStep)
                .build();
    }
}