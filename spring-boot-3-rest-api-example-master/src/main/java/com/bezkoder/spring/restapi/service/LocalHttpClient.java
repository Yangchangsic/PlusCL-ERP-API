package com.bezkoder.spring.restapi.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class LocalHttpClient {

    private final WebClient webClient;

    public LocalHttpClient() {
        this.webClient = createWebClient();
    }

    public Flux<DataBuffer> fetchData(String category1, String begin_date) {

        return  webClient.get()
                .uri("/api/excel?category1=" + category1 + "&begin_date=" + begin_date) // 엔드포인트 설정
                .retrieve()
                .bodyToFlux(DataBuffer.class); // 데이터 버퍼로 응답 받기
    }

    public WebClient createWebClient() {
        // HttpClient에 타임아웃 설정
        reactor.netty.http.client.HttpClient httpClient = reactor.netty.http.client.HttpClient.create()
                .responseTimeout(Duration.ofSeconds(60)); // 응답 타임아웃 설정

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8080") // 기본 URL 설정 (선택)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024)) // 50MB
                        .build())
                .build();
    }

}
