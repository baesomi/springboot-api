package com.prj.sm.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static void printJsonString (HttpServletResponse response, Map obj ) {
		try {
			if (null != response) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(makeMapAsJsonString(obj));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    public static String makeMapAsJsonString(Map map) throws JsonProcessingException  {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(map);
    }
}
