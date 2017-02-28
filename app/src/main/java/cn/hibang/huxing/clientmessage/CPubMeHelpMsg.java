package cn.hibang.huxing.clientmessage;


import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CPubMeHelpMsg extends IMessage{
	
	private static final long serialVersionUID = 520347162437334952L;
	
	private Integer helpUserID;
	private String startTime;
	private String endTime;
	private Integer reqItemID;
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getReqItemID() {
		return reqItemID;
	}

	
	
	public CPubMeHelpMsg() {
		super();
	}
	
	public Integer getHelpUserID() {
		return helpUserID;
	}

	public void setHelpUserID(Integer helpUserID) {
		this.helpUserID = helpUserID;
	}

	
	public void setReqItemID(Integer reqItemID) {
		this.reqItemID = reqItemID;
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
}
