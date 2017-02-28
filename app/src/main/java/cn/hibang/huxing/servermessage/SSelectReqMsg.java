package cn.hibang.huxing.servermessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.UserRequirement;



public class SSelectReqMsg extends IMessage{

	private static final long serialVersionUID = 8512025458068438657L;
	
	private List<UserRequirement> UserReqList = new ArrayList<UserRequirement>();
	
	public SSelectReqMsg() {
		super();
	}

	public List<UserRequirement> getUserReqList() {
		return UserReqList;
	}

	public void setUserReqList(List<UserRequirement> userReqList) {
		UserReqList = userReqList;
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
	
	@Override
	public Class relatedClass() {
		return UserRequirement.class;	
	}
}
