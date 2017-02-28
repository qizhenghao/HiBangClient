package cn.hibang.bruce.domain;

import cn.hibang.huxing.servermessage.SMeHelpMsg;

public class MyMeHelpMsg extends SMeHelpMsg{

	private boolean noRead = true;

	public MyMeHelpMsg(SMeHelpMsg msg) {
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

	public boolean isNoRead() {
		return noRead;
	}

	public void setNoRead(boolean noRead) {
		this.noRead = noRead;
	}
}
