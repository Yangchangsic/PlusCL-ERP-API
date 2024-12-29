package com.bezkoder.spring.restapi.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class TestTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(TestTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        logger.info("test start");
        System.out.println("test start");
        logger.info("test end");
        return RepeatStatus.FINISHED;
    }
}