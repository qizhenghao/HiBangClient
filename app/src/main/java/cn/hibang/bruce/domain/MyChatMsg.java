package cn.hibang.bruce.domain;



public class MyChatMsg{

	private int msgId;
	private int friendId;
	private int senderId;
	private String msgContent;
	private String chatTime;
	private boolean noRead = true;

	public boolean isNoRead() {
		return noRead;
	}

	public void setNoRead(boolean noRead) {
		this.noRead = noRead;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
}
