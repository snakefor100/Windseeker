package com.junlong.windseeker.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by niujunlong on 17/9/29.
 */
public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);


    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static <T> T toObject(String json, Class<T> clazz) {
        T result = null;
        try {
            result = OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            LOG.error("string -> class 转换异常", e);
        }
        return result;
    }


    public static String toString(Object object){
        String result = null;
        try {
            result = OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            LOG.error("class -> string 转换异常", e);
        }
        return result;
    }
}
