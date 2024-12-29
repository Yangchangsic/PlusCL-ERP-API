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

        String begin_date;
        try {
            begin_date = chunkContext.getStepContext()
                    .getJobParameters()
                    .get("currentTime")
                    .toString()
                    .substring(0, 8);
            //begin_date = "20241226";      //날짜 20241226로 고정
            System.out.println("begin_date : " + begin_date);
        } catch (Exception e) {
            return RepeatStatus.FINISHED;
        }
        System.out.println(begin_date);

        try {
            String category1 = "큐어라벨";
            System.out.println(category1);
            List<Map<String, Object>> excelDataList = apiService.getData(begin_date, category1);
            excelDataList.forEach(System.out::println);
            System.out.println(excelDataList.size());

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
            e.printStackTrace();
        }

        try {
            String category2 = "에스더포뮬러";
            System.out.println(category2);
            List<Map<String, Object>> excelDataList2 = apiService.getData(begin_date, category2);
            excelDataList2.forEach(System.out::println);
            System.out.println(excelDataList2.size());

            String path2 = "/Users/USER/Esther_" + begin_date + "_" + excelDataList2.size() + ".xlsx";
            //여기서 윈도우 경로 바꿔야함.

            // 파일이 이미 존재하면 기존 파일을 읽어서 수정, 없으면 새로 생성
            Workbook workbook2 = apiService.getExcel(path2, excelDataList2, category2, begin_date);

            // 파일 저장
            try (FileOutputStream fos = new FileOutputStream(path2)) {
                workbook2.write(fos);
            }

            workbook2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=================================");

        return RepeatStatus.FINISHED;
    }
}