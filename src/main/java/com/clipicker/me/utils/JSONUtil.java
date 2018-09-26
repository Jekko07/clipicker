package com.clipicker.me.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.List;

public class JSONUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static TypeFactory typeFactory = mapper.getTypeFactory();
	
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T> T toObject(String json, Class<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> List<T> parseJsonToList(String jsonString, Class<T> type) {
		List<T> resultList = null;
		try {
		   resultList = mapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, type));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return resultList;
	}
}
