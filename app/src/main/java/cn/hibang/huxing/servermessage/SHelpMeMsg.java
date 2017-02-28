package cn.hibang.huxing.servermessage;



import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;


/*  Send to client while a matched help me message generated*/
public class SHelpMeMsg extends IMessage{
	
	private static final long serialVersionUID = -8958054582261466432L;
	
	private Integer helpme_id;
	
	private String beHelpedName;
	private Integer beHelpedUserID;
	
	private String helpName;
	private Integer helpUserID;
	
	private String msgTime;
	private String startTime;
	private String endTime;
	
	private Integer reqItemID;
	private String reqItem;
	private String reqDetail;
	
	
	public String getMsgTime() {
		return msgTime;
	}



	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
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



	
	
	
	public String getBeHelpedName() {
		return beHelpedName;
	}



	public void setBeHelpedName(String beHelpedName) {
		this.beHelpedName = beHelpedName;
	}



	public Integer getBeHelpedUserID() {
		return beHelpedUserID;
	}



	public void setBeHelpedUserID(Integer beHelpedUserID) {
		this.beHelpedUserID = beHelpedUserID;
	}






	public String getHelpName() {
		return helpName;
	}



	public void setHelpName(String helpName) {
		this.helpName = helpName;
	}



	public Integer getHelpUserID() {
		return helpUserID;
	}



	public void setHelpUserID(Integer helpUserID) {
		this.helpUserID = helpUserID;
	}



	public Integer getReqItemID() {
		return reqItemID;
	}



	public void setReqItemID(Integer reqItemID) {
		this.reqItemID = reqItemID;
	}



	public String getReqItem() {
		return reqItem;
	}



	public void setReqItem(String reqItem) {
		this.reqItem = reqItem;
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



	public Integer getHelpme_id() {
		return helpme_id;
	}



	public void setHelpme_id(Integer helpme_id) {
		this.helpme_id = helpme_id;
	}
}
