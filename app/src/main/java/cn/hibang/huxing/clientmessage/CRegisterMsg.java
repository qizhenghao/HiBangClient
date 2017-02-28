package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.msgutility.HiBangUser;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CRegisterMsg extends IMessage{

	private static final long serialVersionUID = 228093336721293619L;

	private String phone;
	private String name;
	private String password;
	private String email;
	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public CRegisterMsg() {
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
