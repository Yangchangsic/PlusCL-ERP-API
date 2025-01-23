package com.bezkoder.spring.restapi.batch.tasklet;

import com.bezkoder.spring.restapi.service.ApiService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class QuSetHomeProductTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(QuSetHomeProductTasklet.class);

    @Autowired
    ApiService apiService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        logger.info("excel start");

        String begin_date;
        try {
            begin_date = chunkContext.getStepContext()
                    .getJobParameters()
                    .get("currentTime")
                    .toString()
                    .substring(0, 8);
            //begin_date = "20241226";      //날짜 20241226로 고정
            logger.info("begin_date : " + begin_date);
        } catch (Exception e) {
            return RepeatStatus.FINISHED;
        }
        logger.info(begin_date);

        String category1 = "세트상품";
        String category2 = "필름세트 3개입";
        try {
            logger.info(category1);
            List<Map<String, Object>> excelDataList = apiService.getDataForSetHomeProduct(begin_date, category1, category2);
            excelDataList.forEach(data -> logger.info("Excel Data: {}", data));
            logger.info("{}", excelDataList.size());

            String path = "/Users/USER/Curelabel_" + begin_date + "_" + excelDataList.size() + ".xlsx";
            //여기서 윈도우 경로 바꿔야함.

            // 파일이 이미 존재하면 기존 파일을 읽어서 수정, 없으면 새로 생성
            Workbook workbook = apiService.getExcel(path, excelDataList, category1, begin_date);

            // 파일 저장
            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            workbook.close();
        } catch (Exception e) {
            logger.error("category1 : " + category1 + "category2 : " + category2 + ", begin_date : " + begin_date, e);
        }
        logger.info("=================================");

        return RepeatStatus.FINISHED;
    }
}