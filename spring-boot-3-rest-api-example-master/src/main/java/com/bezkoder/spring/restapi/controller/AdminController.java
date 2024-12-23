package com.bezkoder.spring.restapi.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

//----------------------------------------------------------------
//   1. 사용이력을 남기고 싶다면? 
//   2. "ch_order_name"의 공란을 없앨것인지 판단필요, 즉 기존 거래처코드를 100%신뢰를 할 수 있는지 확인 필요 
//   3. 엑셀 열너비 조정 필요 
//   4. 한글의 경우 한글자당 3byte를 잡아먹어서 5글자만되도 15byte로 반영됨 
//   5. TT구분할까요? 외국거래처에 대해서 
//   6. 큐어라벨의 경우, 카카오톡스토어와 카카오톡선물하기 코드가 같으므로 비고란에 스토어/선물하기로 표기

//----------------------------------------------------------------

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/page")
	public String page(Model model) {

		// 데이터를 모델에 추가
        model.addAttribute("title", "Spring Boot 화면 예제");
        model.addAttribute("message", "안녕하세요! Spring Boot와 Thymeleaf를 사용해 화면을 구성합니다.");
        
		
		return "admin";

	}
}
