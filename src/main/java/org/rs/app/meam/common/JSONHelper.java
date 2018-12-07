package org.rs.app.meam.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ra2ldip
 */
public class JSONHelper {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String obectToJSON(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Object cant be converted into JSON");
        }
    }
//    public static <T extends Object> JSONToObject(String json,Class c){
//        return objectMapper.readValue(json, new TypeReference<T>() {
//});
//    }
}
