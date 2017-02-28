package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CLoginMsg extends IMessage {
	private static final long serialVersionUID = 5835779660476950379L;
	
	private String phoneNumber;
	private String password;

	public CLoginMsg() {
		super();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(JSONContext.mapper.writeValueAsString(CLoginMsg.this));
		} catch (JsonProcessingException e) {
			Log.i("11111111111111", e.getMessage());
			e.printStackTrace();
		} catch (JSONException e) {
			Log.i("11111111111111", e.getMessage());
			e.printStackTrace();
		} 
		return getSerialversionuid()+jObj.toString();
	}
	
	
}
