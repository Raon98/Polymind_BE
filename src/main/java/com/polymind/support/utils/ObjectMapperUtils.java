package com.polymind.support.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
    * @ 이미 파싱된 자바 객체를 다른 클래스 인스턴스로 변환
    * */
    public  static <T> T typeConvert(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }

    /**
     * @ JSON 문자열을 자바 객체로 변환
     * */
    public  static <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz); // ✅ 정석
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
}
