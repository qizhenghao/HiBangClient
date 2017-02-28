package cn.hibang.huxing.servermessage;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.HiBangUser;


public class SRegisterMsg extends IMessage{

	private static final long serialVersionUID = -645007463167685085L;
	
	private HiBangUser user;

	public HiBangUser getUser() {
		return user;
	}

	public void setUser(HiBangUser user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
}
