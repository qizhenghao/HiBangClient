package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;



public class CUserInfoRequest extends IMessage {

	private static final long serialVersionUID = -7648024333951010655L;

	
	private Integer userId = 0;
	
	@Override
	public String toString() {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(JSONContext.mapper.writeValueAsString(this));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getSerialversionuid()+jObj.toString();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
