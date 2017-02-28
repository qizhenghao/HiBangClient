package cn.hibang.huxing.servermessage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;


import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.UserRequirement;



public class SRecommendListMsg extends IMessage{
	private static final long serialVersionUID = 7407328401382011451L;
	
	private List<UserRequirement> recommendList = new ArrayList<UserRequirement>();
	
	
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
		return serialVersionUID+jObj.toString();
	}
	
	@Override
	public Class relatedClass() {
		return UserRequirement.class;	
	}
	
	
	public List<UserRequirement> getRecommendList() {
		return recommendList;
	}




	public void setRecommendList(List<UserRequirement> recommendList) {
		this.recommendList = recommendList;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
