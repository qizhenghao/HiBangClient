package cn.hibang.huxing.servermessage;


import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;


public class SChatMessage extends IMessage{

	private static final long serialVersionUID = -1568628042604258604L;
	
	private String chatTime; /*  Message send time */
	private String chatContent; /* Chat message content */
	private String senderName;
	private String receiverName;
	private Integer senderID;
	private Integer receiverID;
	
	
	public SChatMessage() {
		super();
	}

	
	public String getChatTime() {
		return chatTime;
	}


	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}


	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public Integer getSenderID() {
		return senderID;
	}
	public void setSenderID(Integer senderID) {
		this.senderID = senderID;
	}
	public Integer getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(Integer receiverID) {
		this.receiverID = receiverID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String toString() {
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(JSONContext.mapper.writeValueAsString(this));
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSerialversionuid()+jObj.toString();
	} 	
	

}
