package com.bezkoder.spring.restapi.batch;

import com.bezkoder.spring.restapi.service.ApiService;
import org.apache.poi.ss.usermodel.Workbook;
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
public class ExcelWriterTasklet implements Tasklet {

    @Autowired
    ApiService apiService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        System.out.println("excel start");

        String path = "/Users/USER/output.xlsx";
        String begin_date = "20241227";
        String category1 = "큐어라벨";

        List<Map<String, Object>> excelDataList = apiService.getData(begin_date, category1);
        excelDataList.forEach(System.out::println);
        System.out.println(excelDataList.size());

        // 파일이 이미 존재하면 기존 파일을 읽어서 수정, 없으면 새로 생성
        Workbook workbook = apiService.getExcel(path, excelDataList, category1, begin_date);


        // 파일 저장
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }

        workbook.close();
        System.out.println("=================================");

        return RepeatStatus.FINISHED;
    }
}