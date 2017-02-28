package cn.hibang.huxing.clientmessage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CPhotoUpdateMsg extends IMessage{

	private static final long serialVersionUID = -8835707848214391933L;

	private Integer userId;
	private byte[] photo;
	
	public CPhotoUpdateMsg() {
		super();
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
