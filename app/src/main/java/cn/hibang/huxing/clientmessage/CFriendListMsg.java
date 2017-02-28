package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CFriendListMsg extends IMessage{

	private static final long serialVersionUID = 167385036835527666L;
	
	private Integer userID;
	
	private Integer check_code;
	
	public CFriendListMsg() {
		super();
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public Integer getCheck_code() {
		return check_code;
	}
	public void setCheck_code(Integer check_code) {
		this.check_code = check_code;
	} 	
}
