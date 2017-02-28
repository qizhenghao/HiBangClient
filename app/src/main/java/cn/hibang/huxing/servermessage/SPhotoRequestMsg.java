package cn.hibang.huxing.servermessage;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;


public class SPhotoRequestMsg extends IMessage {

	private static final long serialVersionUID = 1788253241049622203L;

	private byte[] photo;

	private Integer userId ;
	
	public SPhotoRequestMsg() {
		super();
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



}
