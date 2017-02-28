package cn.hibang.huxing.msgutility;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;

public class UserRequirement {

	 private Integer helpme_id;
	 private String userName;
	 private Integer userID;
	
	 private Integer reqItemID;
	 private String reqItemName;
	 private String reqDetail;
	
	 private String startTime;
	 private String endTime;
	public Integer getHelpme_id() {
		return helpme_id;
	}
	public void setHelpme_id(Integer helpme_id) {
		this.helpme_id = helpme_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getReqItemName() {
		return reqItemName;
	}
	public void setReqItemName(String reqItemName) {
		this.reqItemName = reqItemName;
	}
	public String getReqDetail() {
		return reqDetail;
	}
	public void setReqDetail(String reqDetail) {
		this.reqDetail = reqDetail;
	}
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
		return jObj.toString();
	}


}
