package com.polymind.support.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClientUtils {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * @ x-www-form-urlencoded 방식 POST 요청
     */
    public static HttpResponse<String> sendPostForm(String url, Map<String,String> params) throws IOException, InterruptedException {
        String encodedFrom = encodeParams(params);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(encodedFrom))
                .build();

        return httpClient.send(request,HttpResponse.BodyHandlers.ofString());
    }

    /**
     * @ aythorization Get 호출
     */
    public static HttpResponse<String> sendAuthorization(String url, String accessToken) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer "+accessToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        return httpClient.send(request,HttpResponse.BodyHandlers.ofString());
    }

    /**
     * @ 파라미터 URL 인코딩
     *
     */
    private static String encodeParams(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
