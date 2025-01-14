package com.bezkoder.spring.restapi.controller;

import com.bezkoder.spring.restapi.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//----------------------------------------------------------------
//   1. 사용이력을 남기고 싶다면? 
//   2. "ch_order_name"의 공란을 없앨것인지 판단필요, 즉 기존 거래처코드를 100%신뢰를 할 수 있는지 확인 필요 
//   3. 엑셀 열너비 조정 필요 
//   4. 한글의 경우 한글자당 3byte를 잡아먹어서 5글자만되도 15byte로 반영됨 
//   5. TT구분할까요? 외국거래처에 대해서 
//   6. 큐어라벨의 경우, 카카오톡스토어와 카카오톡선물하기 코드가 같으므로 비고란에 스토어/선물하기로 표기

//----------------------------------------------------------------

@RestController
@RequestMapping("/api")
public class TutorialController {

    private static final Logger logger = LoggerFactory.getLogger(TutorialController.class);

    @Autowired
    ApiService apiService;

    @GetMapping("/tutorials")
    @ResponseBody
    public String getAllTutorials(
            String category1, String category2, String begin_date
    ) {
        List<Map<String, Object>> excelDataList = apiService.getDataForSetHomeProduct(begin_date, category1, category2);

        excelDataList.forEach(data -> logger.info("Excel Data: {}", data));
        logger.info("size : {}", excelDataList.size());

        return "OK";
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel(String category1, String category2, String begin_date) throws IOException {
        List<Map<String, Object>> excelDataList;
        if (category2 != null && !category2.isEmpty()) {
            excelDataList = apiService.getDataForSetHomeProduct(begin_date, category1, category2);
        } else {
            excelDataList = apiService.getDataForB2C(begin_date, category1);
        }

        byte[] excelData = apiService.getExcel(excelDataList, category1, begin_date);

        // HTTP Response 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + ("큐어라벨".equals(category1) ? "Curelabel" : "Esther") + "_" + begin_date
                                + "_" + excelDataList.size() + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(excelData);
    }
}
