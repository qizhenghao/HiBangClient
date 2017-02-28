package cn.hibang.liuzhiwei.testentity;

import android.R.integer;

public class MyMessage  {

	private String avatar;
	private String time;
	private String distance;
	private String content;
	//用于获取头像的ID
	private Integer avatarId;



	public Integer getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}

	private CONTENT_TYPE contentType;
	private MESSAGE_TYPE messageType;

	public MyMessage(String avatar, String time, String distance, String content,
			CONTENT_TYPE contentType, MESSAGE_TYPE messageType,Integer avatarId) {
		super();
		this.avatar = avatar;
		this.time = time;
		this.distance = distance;
		this.content = content;
		this.contentType = contentType;
		this.messageType = messageType;
		this.avatarId = avatarId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CONTENT_TYPE getContentType() {
		return contentType;
	}

	public void setContentType(CONTENT_TYPE contentType) {
		this.contentType = contentType;
	}

	public MESSAGE_TYPE getMessageType() {
		return messageType;
	}

	public void setMessageType(MESSAGE_TYPE messageType) {
		this.messageType = messageType;
	}

	public enum CONTENT_TYPE {
		TEXT, IMAGE, MAP, VOICE;
	}

	public enum MESSAGE_TYPE {
		RECEIVER, SEND;
	}
}
