package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CPhotoRequestMsg extends IMessage{

	private static final long serialVersionUID = 7005437191621296089L;

	private Integer userID;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CPhotoRequestMsg() {
		super();
	}
	
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
	
}
