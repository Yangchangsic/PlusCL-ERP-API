package com.bezkoder.spring.restapi.service;

import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

@Service
public class DataHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(DataHttpClient.class);

    private final WebClient webClient;

    public DataHttpClient() {
        this.webClient = createWebClient();
    }

    public Map<String, Object> fetchOrderReport(Map<String, Object> body) {
        Map<String, Object> result = webClient.post()
                .uri("/open/order_report") // 엔드포인트 설정
                .header("auth_key", "A77DC277-4F1D-4D91-A488-318B464CBA3A") // 개별 요청에 추가할 헤더
                .bodyValue(body) // 요청 본문 설정
                .retrieve() // 응답 처리 시작
                .bodyToMono(Map.class)
                .retry(3) // 3회 재시도
                .block(); // 비동기를 동기로 변환 (필요 시)
        logger.info("fetchOrderReport body : " + body + ", result : " + result);
        return result;
    }

    public Map<String, Object> fetchStockQty(Map<String, Object> body) {
        Map<String, Object> result = webClient.post()
                .uri("/open/stock_qty") // 엔드포인트 설정
                .header("auth_key", "A77DC277-4F1D-4D91-A488-318B464CBA3A") // 개별 요청에 추가할 헤더
                .bodyValue(body) // 요청 본문 설정
                .retrieve() // 응답 처리 시작
                .bodyToMono(Map.class)
                .retry(3) // 3회 재시도
                .block(); // 비동기를 동기로 변환 (필요 시)
        logger.info("fetchStockQty body : " + body + ", result : " + result);
        return result;
    }

    public WebClient createWebClient() {
        // HttpClient에 타임아웃 설정
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(3600)) // 응답 타임아웃 설정
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000); // 연결 타임아웃 설정

        // WebClient 생성
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://service.pluscl.com") // 기본 URL 설정 (선택)
                .defaultHeader("Content-Type", "application/json") // 기본 헤더 설정 (선택)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024)) // 메모리 제한 설정: 50MB
                        .build())
                .build();
    }

}
