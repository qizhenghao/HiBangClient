package cn.hibang.huxing.clientmessage;
import java.io.Serializable;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


public abstract class IMessage implements Serializable{
	
	
	public abstract String toString();
	
	public Class relatedClass(){
		return null;
	}
}
