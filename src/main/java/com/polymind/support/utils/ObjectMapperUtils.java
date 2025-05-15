package com.polymind.support.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public  static <T> T typeConvert(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }
}
