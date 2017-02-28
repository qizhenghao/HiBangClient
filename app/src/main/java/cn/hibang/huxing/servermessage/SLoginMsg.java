package cn.hibang.huxing.servermessage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.HiBangUser;



public class SLoginMsg extends IMessage{
 
	private static final long serialVersionUID = 5066270957079273798L;
	
	private HiBangUser user = null;
	
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
	
	@Override
	public Class relatedClass() {
		return SLoginMsg.class;
	}

	public HiBangUser getUser() {
		return user;
	}

	public void setUser(HiBangUser user) {
		this.user = user;
	}
}
