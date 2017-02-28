package cn.hibang.huxing.clientmessage;


import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CPubHelpMeMsg extends IMessage{

	private static final long serialVersionUID = -3982778106019620650L;
	
	private Integer userID;
	private Integer reqItemID;
	private String reqDetail;
	private String startTime;
	private String endTime;
	
	
	public CPubHelpMeMsg() {
		super();
	}
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getReqItemID() {
		return reqItemID;
	}
	public void setReqItemID(Integer reqItemID) {
		this.reqItemID = reqItemID;
	}
	public String getReqDetail() {
		return reqDetail;
	}
	public void setReqDetail(String reqDetail) {
		this.reqDetail = reqDetail;
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


	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}	
	
}
