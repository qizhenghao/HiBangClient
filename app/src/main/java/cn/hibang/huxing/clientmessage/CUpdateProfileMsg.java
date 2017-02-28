package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.msgutility.HiBangUser;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CUpdateProfileMsg extends IMessage{
	
	private static final long serialVersionUID = 1364651940871401079L;
	
	private HiBangUser user = null;
	
	
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


	public HiBangUser getUser() {
		return user;
	}


	public void setUser(HiBangUser user) {
		this.user = user;
	}
}
