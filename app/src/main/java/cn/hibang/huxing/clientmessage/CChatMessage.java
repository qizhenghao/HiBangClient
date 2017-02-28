package cn.hibang.huxing.clientmessage;


import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 *  <p>Function : send chat message to server
 *  @author Hu xing
 *  <p>StartTime: 2014-3-4 12:06:29 
 *  <p>LastTime : 2014-3-4 14:05:20
 */
public class CChatMessage extends IMessage{

	private static final long serialVersionUID = 5513940313196507132L;
	
	private String chatTime;
	private String chatContent;
	private Integer userID;
	private Integer friendID;
	
	public CChatMessage() {
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
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getFriendID() {
		return friendID;
	}
	public void setFriendID(Integer friendID) {
		this.friendID = friendID;
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
