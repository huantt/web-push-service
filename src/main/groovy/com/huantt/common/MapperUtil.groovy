package com.huantt.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * @author huantt on 11/5/18
 */
class MapperUtil {

    final public static ObjectMapper objectMapper
    static {
        objectMapper = new ObjectMapper().with {
            setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    public static String writeValueAsString(Object value) {
        return objectMapper.writeValueAsString(value)
    }

    public static <M> M readValue(String json, Class<M> clazz) {
        return objectMapper.readValue(json, clazz)
    }
}
