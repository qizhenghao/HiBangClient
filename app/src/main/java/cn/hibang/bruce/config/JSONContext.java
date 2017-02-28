package cn.hibang.bruce.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONContext {
	public static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}
}
