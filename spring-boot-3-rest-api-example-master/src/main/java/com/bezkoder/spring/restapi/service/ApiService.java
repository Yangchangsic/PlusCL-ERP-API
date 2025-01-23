package com.bezkoder.spring.restapi.service;

import com.bezkoder.spring.restapi.model.ItemCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Autowired
    DataHttpClient dataHttpClient;

    // static Map 선언 및 초기화
    private static final Map<String, String> STATIC_DATA = new HashMap<>();
    private static final Map<String, String> STATIC_DATA2 = new HashMap<>();
    private static final Map<String, String> STATIC_DATA3 = new HashMap<>();
    private static final Map<String, String> STATIC_DATA4 = new HashMap<>();
    private static final Map<String, String> STATIC_DATA5 = new HashMap<>();
    private static final Map<String, Map<String, Object>> ITEM_CODE = new HashMap<>();
    private static final Map<String, List<ItemCode>> ITEM_CODE2 = new HashMap<>();
    private static final Map<String, List<ItemCode>> ITEM_CODE3 = new HashMap<>();

    // static 블록을 사용하여 초기 데이터 삽입
    static {
        STATIC_DATA.put("Cafe24(신) 유튜브쇼핑", "100091");
        STATIC_DATA.put("고도몰5", "100108");
        STATIC_DATA.put("스마트스토어", "100113");
        STATIC_DATA.put("아임웹", "00698");
        STATIC_DATA.put("카카오톡스토어", "00425");
        STATIC_DATA.put("쿠팡", "00194");
        STATIC_DATA.put("펫프렌즈", "100152");
        STATIC_DATA.put("카카오톡선물하기", "00425");
    }

    // static 블록을 사용하여 초기 데이터 삽입
    static {
        STATIC_DATA2.put("고도몰5", "1000000003");
        STATIC_DATA2.put("카카오톡선물하기", "1000000331");
        STATIC_DATA2.put("SK스토어", "03549");
        STATIC_DATA2.put("GS shop", "02926");
        STATIC_DATA2.put("Wconcept", "02698");
        STATIC_DATA2.put("신세계몰(신)", "02252");
        STATIC_DATA2.put("롯데온", "02554");
        STATIC_DATA2.put("카카오톡스토어", "02490");
        STATIC_DATA2.put("스마트스토어", "02363");
        STATIC_DATA2.put("신세계TV쇼핑", "02603");
        STATIC_DATA2.put("K쇼핑", "01552");
        STATIC_DATA2.put("홈&쇼핑", "00649");
        STATIC_DATA2.put("NS홈쇼핑(신)", "00343");
        STATIC_DATA2.put("롯데홈쇼핑(신)", "00341");
        STATIC_DATA2.put("현대홈쇼핑(3)", "00251");
        STATIC_DATA2.put("CJ온스타일", "00250");
        STATIC_DATA2.put("쿠팡", "03985");
        STATIC_DATA2.put("AliExpress", "1000001491");
        STATIC_DATA2.put("써드웨이컴퍼니", "03612");
    }

    static {
        STATIC_DATA3.put("카카오톡스토어", "스토어");
        STATIC_DATA3.put("카카오톡선물하기", "선물하기");
    }

    static {
        STATIC_DATA4.put("홈앤쇼핑", "00649");
        STATIC_DATA4.put("K쇼핑", "01552");
        STATIC_DATA4.put("NS홈쇼핑", "00343");
        STATIC_DATA4.put("CJ온스타일", "00250");
        STATIC_DATA4.put("신세계티비쇼핑", "02603");
        STATIC_DATA4.put("롯데", "00341");
        STATIC_DATA4.put("sk스토어", "03549");
        STATIC_DATA4.put("현대홈쇼핑", "00251");
        STATIC_DATA4.put("GS홈쇼핑", "02926");
    }

    static {
        STATIC_DATA5.put("홈앤쇼핑", "6854684");
        STATIC_DATA5.put("K쇼핑", "01552");
        STATIC_DATA5.put("NS홈쇼핑", "00343");
        STATIC_DATA5.put("CJ온스타일", "00250");
        STATIC_DATA5.put("신세계티비쇼핑", "02603");
        STATIC_DATA5.put("롯데", "00341");
        STATIC_DATA5.put("sk스토어", "03549");
        STATIC_DATA5.put("현대홈쇼핑", "00251");
        STATIC_DATA5.put("GS홈쇼핑", "02926");
    }

    static {
        ITEM_CODE.put("CCM3", Map.of(
                "singleCode", "PC_EF_1030",
                "productName", "[필름세트]커큐민  3개입",
                "qty", 3
        ));
        ITEM_CODE.put("CCM12", Map.of(
                "singleCode", "PC_EF_1030",
                "productName", "[필름세트]커큐민  12개입",
                "qty", 12
        ));
        ITEM_CODE.put("ECF3", Map.of(
                "singleCode", "PC_EF_1052",
                "productName", "[필름세트]엘라스틴&콜라겐  3개입",
                "qty", 3
        ));
        ITEM_CODE.put("GBF3", Map.of(
                "singleCode", "PC_EF_1069",
                "productName", "[필름세트]가바  3개입",
                "qty", 3
        ));
        ITEM_CODE.put("GFK12", Map.of(
                "singleCode", "PC_EF_1072",
                "productName", "[필름세트]글루타치온 키즈 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("GFK3", Map.of(
                "singleCode", "PC_EF_1072",
                "productName", "[필름세트]글루타치온 키즈 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("GFM12", Map.of(
                "singleCode", "PC_EF_1073",
                "productName", "[필름세트]글루타치온 맨즈 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("GFM3", Map.of(
                "singleCode", "PC_EF_1073",
                "productName", "[필름세트]글루타치온 맨즈 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("GFS3", Map.of(
                "singleCode", "PC_EF_1076",
                "productName", "[필름세트]글루타치온 실버 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("GFS12", Map.of(
                "singleCode", "PC_EF_1076",
                "productName", "[필름세트]글루타치온 실버 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("GFX3", Map.of(
                "singleCode", "PC_EF_1079",
                "productName", "[필름세트]글루타치온 5X <3개입>",
                "qty", 3
        ));
        ITEM_CODE.put("GFX12", Map.of(
                "singleCode", "PC_EF_1079",
                "productName", "[필름세트]글루타치온 5X <12개입>",
                "qty", 12
        ));
        ITEM_CODE.put("GLT3", Map.of(
                "singleCode", "PC_EF_1080",
                "productName", "글루타치온다이렉트필름",
                "qty", 3
        ));
        ITEM_CODE.put("GDT3", Map.of(
                "singleCode", "PC_EF_1082",
                "productName", "[필름세트]글루타치온 3X <3개입>",
                "qty", 3
        ));
        ITEM_CODE.put("GDT12", Map.of(
                "singleCode", "PC_EF_1082",
                "productName", "[필름세트]글루타치온 3X 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("PGD3", Map.of(
                "singleCode", "PC_EF_1149",
                "productName", "[필름세트]프로테오글리칸 2X  <3개입>",
                "qty", 3
        ));
        ITEM_CODE.put("PGD12", Map.of(
                "singleCode", "PC_EF_1149",
                "productName", "[필름세트]프로테오글리칸 2X  <12개입>",
                "qty", 12
        ));
        ITEM_CODE.put("PDC3", Map.of(
                "singleCode", "PC_EF_1153",
                "productName", "[필름세트]프로테오글리칸 콘드 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("PDC12", Map.of(
                "singleCode", "PC_EF_1153",
                "productName", "[필름세트]프로테오글리칸 콘드 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("SAC3", Map.of(
                "singleCode", "PC_EF_1180",
                "productName", "[필름세트]SAC  3개입",
                "qty", 3
        ));
        ITEM_CODE.put("SAC12", Map.of(
                "singleCode", "PC_EF_1180",
                "productName", "[필름세트]SAC 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("SDD3", Map.of(
                "singleCode", "PC_EF_1182",
                "productName", "[필름세트]연어DNA 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("LGD3", Map.of(
                "singleCode", "PC_EF_1207",
                "productName", "[필름세트]리포좀글루타치온 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("LGD12", Map.of(
                "singleCode", "PC_EF_1207",
                "productName", "[필름세트]리포좀글루타치온 12개입",
                "qty", 12
        ));
        ITEM_CODE.put("RGD3", Map.of(
                "singleCode", "PC_EF_1213",
                "productName", "[필름세트]유기농 홍삼 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("NMN3", Map.of(
                "singleCode", "PC_EF_1245",
                "productName", "여에스더 NMN 파이토에스 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("NMX3", Map.of(
                "singleCode", "PC_EF_1251",
                "productName", "[필름세트]NMN 2X 3개입",
                "qty", 3
        ));
        ITEM_CODE.put("PVS6", Map.of(
                "singleCode", "PC_YN_3003",
                "productName", "영라뉴 펌킨 브이샷",
                "qty", 6
        ));
    }

    static {
        ITEM_CODE2.put("1207RE+1212", List.of(
                        new ItemCode(
                                "PC_EF_1207",
                                "리포좀 글루타치온 다이렉트 울트라X 리뉴얼",
                                12,
                                "20270818"
                        ), new ItemCode(
                                "PC_EF_1212",
                                "루테인지아잔틴 다이렉트 RS2",
                                1,
                                "20260313"
                        )
                )
        );
        ITEM_CODE2.put("1207SE 13", List.of(
                        new ItemCode(
                                "PC_EF_1207",
                                "리포좀 글루타치온 다이렉트 울트라X 리뉴얼 2",
                                13,
                                "20271110"
                        )
                )
        );
    }


    static {
        ITEM_CODE3.put("1207RE+1212", List.of(
                        new ItemCode(
                                "PC_EF_1207",
                                "리포좀 글루타치온 다이렉트 울트라X 리뉴얼",
                                12,
                                "20270818"
                        ), new ItemCode(
                                "PC_EF_1212",
                                "루테인지아잔틴 다이렉트 RS2",
                                1,
                                "20260313"
                        )
                )
        );
        ITEM_CODE3.put("1207SE 13", List.of(
                        new ItemCode(
                                "PC_EF_1207",
                                "리포좀 글루타치온 다이렉트 울트라X 리뉴얼 2",
                                13,
                                "20271110"
                        )
                )
        );
        ITEM_CODE3.put("124911 20", List.of(
                        new ItemCode(
                                "PC_EF_1211",
                                "맥주효모 비오틴 울트라 5200",
                                11,
                                "20260918"
                        )
                )
        );
    }


    public List<Map<String, Object>> getDataForB2C(String begin_date, String category1) {
        List<Map<String, Object>> excelDataList = new ArrayList<>();
        int page = 1;
        AtomicInteger count = new AtomicInteger();

        while (true) {
            // 최상위 Map
            Map<String, Object> body = new HashMap<>();

            // 첫 번째 레벨 데이터 추가
            body.put("company_code", "B201");
            body.put("warehouse_code", "AFAX");
            body.put("warehouse_type_code", "0000");
            body.put("seller_code", "B201");
            body.put("job_type", "search");
            body.put("type", "out");

            // "data" 키에 들어갈 중첩 Map 생성
            Map<String, Object> data = new HashMap<>();
            data.put("begin_date", begin_date);
            data.put("end_date", begin_date);
            data.put("ord_kind1", "0100");
            data.put("warehouse_list", "AFAX");
            data.put("category1", category1);
            data.put("page", String.valueOf(page));

            // "data" Map을 최상위 Map에 추가
            body.put("data", data);

            Map<String, Object> result = dataHttpClient.fetchOrderReport(body);

            String r_code = (String) result.get("r_code");
            if (!"0".equals(r_code)) {
                //todo

                return new ArrayList<>();
            }

            List<Map<String, Object>> resultDataList = (List<Map<String, Object>>) result.get("data");

            if (resultDataList.isEmpty()) {
                break;
            }

            List<Map<String, Object>> test = resultDataList.stream().filter(map -> {
                Object itemCode = map.get("item_code");
                return itemCode != null && itemCode.toString().getBytes().length > 9;
            }).map(map -> {
                Map<String, Object> transformedMap = new HashMap<>();
                transformedMap.put("row_id", map.get("row_id"));
                transformedMap.put("ord_name", map.get("ord_name"));

                String ordName = (String) map.get("ord_name");
                if ("에스더포뮬러".equals(category1)) {
                    if (StringUtils.isEmpty(ordName) || "(주)에스더포뮬러".equals(ordName) || "에*사".equals(ordName)) {
                        String ordSiteUserId = (String) map.get("ord_site_user_id");
                        String chOrderName = STATIC_DATA4.get(ordSiteUserId);
                        transformedMap.put("ch_order_name", chOrderName);
                        count.getAndIncrement();
                    } else {
                        transformedMap.put("ch_order_name", STATIC_DATA2.get(ordName));
                    }
                } else {
                    //큐어라벨
                    if (StringUtils.isEmpty(ordName)) {
                        String ordSiteUserId = (String) map.get("ord_site_user_id");
                        String chOrderName = STATIC_DATA5.get(ordSiteUserId);
                        transformedMap.put("ch_order_name", chOrderName);
                        count.getAndIncrement();
                    } else {
                        transformedMap.put("ch_order_name", STATIC_DATA.get(ordName));
                    }
                }

                String expireDay = "*";

                transformedMap.put("lot_no", map.get("lot_no"));
                if (StringUtils.isEmpty((String) map.get("lot_no"))) {
                    transformedMap.put("lot_no", "-");
                    expireDay = "-";
                } else {
                    String sheifLiftUnit = (String) map.get("SheifLift_Unit");
                    Integer sheifLift = (Integer) map.get("SheifLift");

                    String lotNo = (String) map.get("lot_no");

                    //lotNo가 있을 때만
                    if (lotNo != null && !lotNo.isEmpty()) {
                        // 날짜 형식 지정
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                        // lot_no를 LocalDate로 변환
                        LocalDate lotDate = LocalDate.parse(lotNo, formatter);
                        // SheifLift_Unit이 "M"인 경우
                        if ("M".equalsIgnoreCase(sheifLiftUnit)) {
                            lotDate = lotDate.plusMonths(sheifLift).minusDays(1);
                            expireDay = lotDate.format(formatter);
                        } else if ("D".equals(sheifLiftUnit)) {
                            lotDate = lotDate.plusDays(sheifLift).minusDays(1);
                            expireDay = lotDate.format(formatter);
                        } else if ("Y".equalsIgnoreCase(sheifLiftUnit)) { // ++ 연도 추가
                            lotDate = lotDate.plusYears(sheifLift).minusDays(1);
                            expireDay = lotDate.format(formatter);
                        }
                    }
                }

                transformedMap.put("expireDay", expireDay);

                String itemCode = (String) map.get("item_code");
                transformedMap.put("item_code", itemCode.substring(0, Math.min(11, itemCode.length())));
                transformedMap.put("qty", map.get("qty"));
                return transformedMap;
            }).collect(Collectors.toList());
            excelDataList.addAll(test);
            page++;
        }
        logger.debug("count : " + count.get());
        return excelDataList;
    }

    public List<Map<String, Object>> getDataForSetProduct(String begin_date, String category1, String category2) {
        List<Map<String, Object>> excelDataList = new ArrayList<>();
        int page = 1;
        try {
            while (true) {
                // 최상위 Map
                Map<String, Object> body = new HashMap<>();

                // 첫 번째 레벨 데이터 추가
                body.put("company_code", "B201");
                body.put("warehouse_code", "AFAX");
                body.put("warehouse_type_code", "0000");
                body.put("seller_code", "B201");
                body.put("job_type", "search");
                body.put("type", "out");

                // "data" 키에 들어갈 중첩 Map 생성
                Map<String, Object> data = new HashMap<>();
                data.put("begin_date", begin_date);
                data.put("end_date", begin_date);
                data.put("ord_kind1", "0100");
                data.put("warehouse_list", "AFAX");
                data.put("category1", category1);
                data.put("category2", category2);
                data.put("page", String.valueOf(page));

                // "data" Map을 최상위 Map에 추가
                body.put("data", data);

                Map<String, Object> result = dataHttpClient.fetchOrderReport(body);

                String r_code = (String) result.get("r_code");
                if (!"0".equals(r_code)) {
                    //todo
                    logger.error(category1 + " 실패입니다.");
                    return new ArrayList<>();
                }

                List<Map<String, Object>> resultDataList = (List<Map<String, Object>>) result.get("data");

                if (resultDataList.isEmpty()) {
                    break;
                }

                List<Map<String, Object>> test = resultDataList.stream().filter(map -> {
                            Object itemCode = map.get("item_code");
                            return itemCode != null;
                        }).map(map -> {
                            Map<String, Object> transformedMap = new HashMap<>();
                            transformedMap.put("row_id", map.get("row_id"));
                            transformedMap.put("ord_name", map.get("ord_name"));

                            String ordName = (String) map.get("ord_name");
                            if (ordName == null || ordName.isEmpty() || "(주)에스더포뮬러".equals(ordName) || "에*사".equals(ordName)) {
                                String ordSiteUserId = (String) map.get("ord_site_user_id");
                                String chOrderName = STATIC_DATA4.get(ordSiteUserId);
                                transformedMap.put("ch_order_name", chOrderName);
                            } else {
                                transformedMap.put("ch_order_name", STATIC_DATA2.get(ordName));
                            }

                            String itemCode = (String) map.get("item_code");
                            String sheifLiftUnit = (String) map.get("SheifLift_Unit");
                            Integer sheifLift = (Integer) map.get("SheifLift");

                            String lotNo = Optional.ofNullable((String) map.get("lot_no")).orElse("");
                            transformedMap.put("lot_no", lotNo);

                            int qty = (int) map.get("qty");

                            if (ITEM_CODE.get(itemCode) == null) {
                                transformedMap.put("item_code", "");
                                transformedMap.put("qty", -1);
                            } else {
                                transformedMap.put("item_code", ITEM_CODE.get(itemCode).get("singleCode"));
                                transformedMap.put("qty", qty * (Integer) ITEM_CODE.get(itemCode).get("qty"));
                            }

                            String expireDay = "*";
                            if (!lotNo.isEmpty()) {
                                // 날짜 형식 지정
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                                // lot_no를 LocalDate로 변환
                                LocalDate lotDate;
                                try {
                                    lotDate = LocalDate.parse(lotNo, formatter);
                                } catch (Exception e) {
                                    logger.error("map : " + map + ", lotNo: " + lotNo, e);
                                    throw e;
                                }

                                // SheifLift_Unit이 "M"인 경우
                                if ("M".equalsIgnoreCase(sheifLiftUnit)) {
                                    lotDate = lotDate.plusMonths(sheifLift).minusDays(1);
                                    expireDay = lotDate.format(formatter);
                                } else if ("D".equals(sheifLiftUnit)) {
                                    lotDate = lotDate.plusDays(sheifLift).minusDays(1);
                                    expireDay = lotDate.format(formatter);
                                } else if ("Y".equalsIgnoreCase(sheifLiftUnit)) { // ++ 연도 추가
                                    lotDate = lotDate.plusYears(sheifLift).minusDays(1);
                                    expireDay = lotDate.format(formatter);
                                }
                            }

                            transformedMap.put("expireDay", expireDay);

                            return transformedMap;
                        }) // 빈 Map 제거
                        .toList();
                excelDataList.addAll(test);
                page++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return excelDataList;
    }

    public List<Map<String, Object>> getDataForSetHomeProduct(String begin_date, String category1, String category2) {
        List<Map<String, Object>> excelDataList = new ArrayList<>();
        int page = 1;
        try {
            while (true) {
                // 최상위 Map
                Map<String, Object> body = new HashMap<>();

                // 첫 번째 레벨 데이터 추가
                body.put("company_code", "B201");
                body.put("warehouse_code", "AFAX");
                body.put("warehouse_type_code", "0000");
                body.put("seller_code", "B201");
                body.put("job_type", "search");
                body.put("type", "out");

                // "data" 키에 들어갈 중첩 Map 생성
                Map<String, Object> data = new HashMap<>();
                data.put("begin_date", begin_date);
                data.put("end_date", begin_date);
                data.put("ord_kind1", "0100");
                data.put("warehouse_list", "AFAX");
                data.put("category1", category1);
                data.put("category2", category2);
                data.put("page", String.valueOf(page));

                // "data" Map을 최상위 Map에 추가
                body.put("data", data);

                Map<String, Object> result = dataHttpClient.fetchOrderReport(body);

                String r_code = (String) result.get("r_code");
                if (!"0".equals(r_code)) {
                    //todo
                    logger.error(category1 + " 실패입니다.");
                    return new ArrayList<>();
                }

                List<Map<String, Object>> resultDataList = (List<Map<String, Object>>) result.get("data");

                if (resultDataList.isEmpty()) {
                    break;
                }

                List<Map<String, Object>> test = resultDataList.stream().filter(map -> {
                            Object itemCode = map.get("item_code");
                            return itemCode != null;
                        }).map(map -> {
                            int qty = (int) map.get("qty");
                            String itemCode = (String) map.get("item_code");    //1207RE+1212
                            String ordSiteUserId = (String) map.get("ord_site_user_id");

                            List<ItemCode> list;
                            String chOrderName;
                            if ("QU멀티세트".equals(ordSiteUserId)) {
                                list = ITEM_CODE3.get(itemCode);
                                chOrderName = STATIC_DATA5.get(ordSiteUserId);
                            } else {
                                list = ITEM_CODE2.get(itemCode);
                                chOrderName = STATIC_DATA4.get(ordSiteUserId);
                            }

                            return list.stream()
                                    .map(itemCode2 -> {
                                        Map<String, Object> transformedMap = new HashMap<>();
                                        transformedMap.put("row_id", map.get("row_id"));
                                        transformedMap.put("ord_name", map.get("ord_name"));
                                        transformedMap.put("item_code", itemCode2.getSingleCode());
                                        transformedMap.put("ch_order_name", chOrderName);
                                        transformedMap.put("qty", qty * itemCode2.getQty());
                                        transformedMap.put("expireDay", itemCode2.getExpireDay());
                                        return transformedMap;
                                    })
                                    .collect(Collectors.toList());
                        })
                        .flatMap(Collection::stream) // 리스트를 평탄화
                        .filter(map -> !map.isEmpty()) // 빈 Map 제거
                        .toList();
                excelDataList.addAll(test);
                page++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return excelDataList;
    }

    public byte[] getExcel(List<Map<String, Object>> excelDataList, String category1, String begin_date) throws IOException {
        // Excel Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        List<List<String>> nestedList = new ArrayList<>();

        // 내부 리스트 생성 및 데이터 추가
        List<String> list1 = new ArrayList<>();
        list1.add("거래구분");
        list1.add("출고일자");
        list1.add("고객코드");
        list1.add("환종");
        list1.add("환율");
        list1.add("과세구분");
        list1.add("단가구분");
        list1.add("창고코드");
        list1.add("LOT번호");
        list1.add("담당자코드");
        list1.add("비고(건)");
        list1.add("품번");
        list1.add("주문단위수량");
        list1.add("재고단위수량");
        list1.add("장소코드");
        list1.add("비고(내역)");

        List<String> list2 = new ArrayList<>();
        list2.add("SO_FG");
        list2.add("ISU_DT");
        list2.add("TR_CD");
        list2.add("EXCH_CD");
        list2.add("EXCH_RT");
        list2.add("VAT_FG");
        list2.add("UMVAT_FG");
        list2.add("WH_CD");
        list2.add("LOT_NB");
        list2.add("PLN_CD");
        list2.add("REMARK_DC");
        list2.add("ITEM_CD");
        list2.add("SO_QT");
        list2.add("ISU_QT");
        list2.add("LC_CD");
        list2.add("REMARK_DC_D");

        List<String> list3 = new ArrayList<>();
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n"
                + "설명 : 숫자만 입력하세요. (0.DOMESTIC, 1.LOCAL L/C, 2.구매승인서, 3.MASTER L/C, 4.T/T, 5.D/A, 6.D/P)");
        list3.add("타입 : 날짜\n" + "길이 : 8\n" + "필수 : True\n" + "설명 : 숫자 기준 8자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 10\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 10자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n" + "설명 : 숫자 1자리(최대)를 입력 하세요.(0.매출과세 1.수출영세 2.매출면세 3. 매출기타)");
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n" + "설명 : 숫자 1자리(최대)를 입력 하세요.(0. 부가세미포함 1.부가세포함)");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 50\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 50자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 10\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 10자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 60\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 60자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 30\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 30자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 60\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 60자리(최대)를 입력 하세요.");

        // 내부 리스트를 nestedList에 추가
        nestedList.add(list1);
        nestedList.add(list2);
        nestedList.add(list3);

        for (int i = 0; i < nestedList.size(); i++) {
            List<String> nested = nestedList.get(i);
            Row headerRow = sheet.createRow(i);

            if (i == 2) {
                headerRow.setHeightInPoints(160); // 높이를 30포인트로 설정

            }

            for (int j = 0; j < nested.size(); j++) {
                Cell cell = headerRow.createCell(j);
                cell.setCellValue(nested.get(j));
            }
        }

        for (int i = 0; i < excelDataList.size(); i++) {
            Row row = sheet.createRow(i + 3);

            row.createCell(0).setCellValue(0);

            row.createCell(1).setCellValue(begin_date);

//			row.createCell(1).setCellValue(begin_date.substring(0, 4).concat("-").concat(begin_date.substring(4, 6)) + 다시사용안해도 엑셀업로드됨
//					.concat("-").concat(begin_date.substring(6, 8)));

            Map<String, Object> excelData = excelDataList.get(i);

            try {
//				row.createCell(2).setCellValue(Integer.valueOf((String) excelDataList.get(i).get("ch_order_name"))); + 추가 텍스트값으로 해야함 안그러면 00이 숫자로 인식해서 엑셀변환시 안나타남
                row.createCell(2).setCellValue(((String) excelData.get("ch_order_name")));

            } catch (Exception e) {
                row.createCell(2).setCellValue("");
            }

            row.createCell(3).setCellValue("KRW");

            row.createCell(4).setCellValue(1.000);

            row.createCell(5).setCellValue(0);

            row.createCell(6).setCellValue(0);

            if ("큐어라벨".equals(category1)) {
                row.createCell(7).setCellValue("0007");
            } else {
                // 퓨어 면 수정 하면 됨.
                row.createCell(7).setCellValue("0007");
            }

            try {
                row.createCell(8).setCellValue(Integer.valueOf((String) excelData.get("expireDay")));

            } catch (Exception e) {
                row.createCell(8).setCellValue("-");
            }

            row.createCell(9).setCellValue(24012902);

            row.createCell(10).setCellValue("B2C".concat(begin_date.substring(4, 8)));

            row.createCell(11).setCellValue((String) excelData.get("item_code"));

            row.createCell(12).setCellValue((Integer) excelData.get("qty"));

            row.createCell(13).setCellValue((Integer) excelData.get("qty"));

            row.createCell(14).setCellValue("008");

            String ordName = (String) excelData.get("ord_name");

            if ("큐어라벨".equals(category1) && STATIC_DATA3.get(ordName) != null) {
                row.createCell(15).setCellValue(STATIC_DATA3.get(ordName));
            } else {
                row.createCell(15).setCellValue("");
            }

            row.createCell(16).setCellValue((Integer) excelData.get("row_id"));
        }

        // Excel 파일을 ByteArray로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        byte[] excelData = outputStream.toByteArray();
        return excelData;
    }

    public Workbook getExcel(String path, List<Map<String, Object>> excelDataList, String category1, String begin_date) throws IOException {
        // Excel Workbook 생성
        Workbook workbook = new File(path).exists()
                ? WorkbookFactory.create(new File(path))
                : new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        List<List<String>> nestedList = new ArrayList<>();

        // 내부 리스트 생성 및 데이터 추가
        List<String> list1 = new ArrayList<>();
        list1.add("거래구분");
        list1.add("출고일자");
        list1.add("고객코드");
        list1.add("환종");
        list1.add("환율");
        list1.add("과세구분");
        list1.add("단가구분");
        list1.add("창고코드");
        list1.add("LOT번호");
        list1.add("담당자코드");
        list1.add("비고(건)");
        list1.add("품번");
        list1.add("주문단위수량");
        list1.add("재고단위수량");
        list1.add("장소코드");
        list1.add("비고(내역)");

        List<String> list2 = new ArrayList<>();
        list2.add("SO_FG");
        list2.add("ISU_DT");
        list2.add("TR_CD");
        list2.add("EXCH_CD");
        list2.add("EXCH_RT");
        list2.add("VAT_FG");
        list2.add("UMVAT_FG");
        list2.add("WH_CD");
        list2.add("LOT_NB");
        list2.add("PLN_CD");
        list2.add("REMARK_DC");
        list2.add("ITEM_CD");
        list2.add("SO_QT");
        list2.add("ISU_QT");
        list2.add("LC_CD");
        list2.add("REMARK_DC_D");

        List<String> list3 = new ArrayList<>();
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n"
                + "설명 : 숫자만 입력하세요. (0.DOMESTIC, 1.LOCAL L/C, 2.구매승인서, 3.MASTER L/C, 4.T/T, 5.D/A, 6.D/P)");
        list3.add("타입 : 날짜\n" + "길이 : 8\n" + "필수 : True\n" + "설명 : 숫자 기준 8자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 10\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 10자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n" + "설명 : 숫자 1자리(최대)를 입력 하세요.(0.매출과세 1.수출영세 2.매출면세 3. 매출기타)");
        list3.add("타입 : 문자\n" + "길이 : 1\n" + "필수 : True\n" + "설명 : 숫자 1자리(최대)를 입력 하세요.(0. 부가세미포함 1.부가세포함)");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 50\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 50자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 10\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 10자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 60\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 60자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 30\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 30자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 숫자\n" + "길이 : 17,6\n" + "필수 : True\n" + "설명 : 숫자 기준 17,6자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 4\n" + "필수 : True\n" + "설명 : 영문/숫자 기준 4자리(최대)를 입력 하세요.");
        list3.add("타입 : 문자\n" + "길이 : 60\n" + "필수 : False\n" + "설명 : 영문/숫자 기준 60자리(최대)를 입력 하세요.");

        // 내부 리스트를 nestedList에 추가
        nestedList.add(list1);
        nestedList.add(list2);
        nestedList.add(list3);

        for (int i = 0; i < nestedList.size(); i++) {
            List<String> nested = nestedList.get(i);
            Row headerRow = sheet.createRow(i);

            if (i == 2) {
                headerRow.setHeightInPoints(160); // 높이를 30포인트로 설정

            }

            for (int j = 0; j < nested.size(); j++) {
                Cell cell = headerRow.createCell(j);
                cell.setCellValue(nested.get(j));
            }
        }

        for (int i = 0; i < excelDataList.size(); i++) {
            Row row = sheet.createRow(i + 3);

            row.createCell(0).setCellValue(0);

            row.createCell(1).setCellValue(begin_date);

//			row.createCell(1).setCellValue(begin_date.substring(0, 4).concat("-").concat(begin_date.substring(4, 6)) + 다시사용안해도 엑셀업로드됨
//					.concat("-").concat(begin_date.substring(6, 8)));

            Map<String, Object> excelData = excelDataList.get(i);
            logger.info("excelData  :" + excelData);

            try {
//				row.createCell(2).setCellValue(Integer.valueOf((String) excelDataList.get(i).get("ch_order_name"))); + 추가 텍스트값으로 해야함 안그러면 00이 숫자로 인식해서 엑셀변환시 안나타남
                row.createCell(2).setCellValue(((String) excelData.get("ch_order_name")));

            } catch (Exception e) {
                row.createCell(2).setCellValue("");
            }

            row.createCell(3).setCellValue("KRW");

            row.createCell(4).setCellValue(1.000);

            row.createCell(5).setCellValue(0);

            row.createCell(6).setCellValue(0);

            if ("큐어라벨".equals(category1)) {
                row.createCell(7).setCellValue("0007");
            } else {
                // 퓨어 면 수정 하면 됨.
                row.createCell(7).setCellValue("0007");
            }

            try {
                row.createCell(8).setCellValue(Integer.valueOf((String) excelData.get("expireDay")));

            } catch (Exception e) {
                row.createCell(8).setCellValue("-");
            }

            row.createCell(9).setCellValue(24012902);

            row.createCell(10).setCellValue("B2C".concat(begin_date.substring(4, 8)));

            row.createCell(11).setCellValue((String) excelData.get("item_code"));

            row.createCell(12).setCellValue((Integer) excelData.get("qty"));

            row.createCell(13).setCellValue((Integer) excelData.get("qty"));

            row.createCell(14).setCellValue("008");

            String ordName = (String) excelData.get("ord_name");

            if ("큐어라벨".equals(category1) && STATIC_DATA3.get(ordName) != null) {
                row.createCell(15).setCellValue(STATIC_DATA3.get(ordName));
            } else {
                row.createCell(15).setCellValue("");
            }

            row.createCell(16).setCellValue((Integer) excelData.get("row_id"));
        }
        return workbook;
    }
}
