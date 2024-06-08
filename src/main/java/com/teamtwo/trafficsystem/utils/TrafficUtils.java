package com.teamtwo.trafficsystem.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamtwo.trafficsystem.domain.TrafficData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class TrafficUtils {

    WebClient webClient;
    public JsonNode getExchangeDataSync() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        int bufferSize = 16 * 1024 * 1024; // 16MB

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(bufferSize))
            .build();

        webClient = WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build();


        String responseBody = webClient.get()
            .uri(builder -> builder
                .scheme("https")
                .host("data.ex.co.kr")
                .path("/openapi/odtraffic/trafficAmountByRealtime")
                .queryParam("key", "test")
                .queryParam("type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return parseJson(responseBody);
    }

    private JsonNode parseJson(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            return root.path("list");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TrafficData> getExchangeDataAsDtoList() {
        JsonNode jsonNode = getExchangeDataSync();

        if (jsonNode != null && jsonNode.isArray()) {
            List<TrafficData> exchangeDtoList = new ArrayList<>();

            for (JsonNode node : jsonNode) {
                TrafficData trafficData = convertJsonToExchangeDto(node);
                exchangeDtoList.add(trafficData);
            }

            return exchangeDtoList;
        }

        return Collections.emptyList();
    }

    private TrafficData convertJsonToExchangeDto(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(jsonNode, TrafficData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
