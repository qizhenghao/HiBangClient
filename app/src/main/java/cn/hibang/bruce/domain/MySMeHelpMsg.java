package cn.hibang.bruce.domain;

import cn.hibang.huxing.servermessage.SMeHelpMsg;

public class MySMeHelpMsg extends SMeHelpMsg{
	

	private int id;
	private boolean noRead = true;

	public MySMeHelpMsg(SMeHelpMsg msg) {
		this.setBeHelpedUserID(msg.getBeHelpedUserID());
		this.setBeHelpedName(msg.getBeHelpedName());
		this.setEndTime(msg.getEndTime());
		this.setStartTime(msg.getStartTime());
		this.setHelpme_id(msg.getHelpme_id());
		this.setHelpName(msg.getHelpName());
		this.setHelpUserID(msg.getHelpUserID());
		this.setMsgTime(msg.getMsgTime());
		this.setNoRead(true);
		this.setReqDetail(msg.getReqDetail());
		this.setReqItem(msg.getReqItem());
		this.setReqItemID(msg.getReqItemID());
		
	}

	
	public MySMeHelpMsg() {
		// TODO Auto-generated constructor stub
	}


	public boolean isNoRead() {
		return noRead;
	}

	public void setNoRead(boolean noRead) {
		this.noRead = noRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}