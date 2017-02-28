package cn.hibang.bruce.domain;

import cn.hibang.huxing.servermessage.SHelpMeMsg;

public class MySHelpMeMsg extends SHelpMeMsg{

	private int id;
	private boolean noRead = true;

	public MySHelpMeMsg(SHelpMeMsg msg) {
		this.setBeHelpedName(msg.getBeHelpedName());
		this.setBeHelpedUserID(msg.getBeHelpedUserID());
		this.setEndTime(msg.getEndTime());
		this.setHelpme_id(msg.getHelpme_id());
		this.setHelpName(msg.getHelpName());
		this.setHelpUserID(msg.getHelpUserID());
		this.setMsgTime(msg.getMsgTime());
		this.setNoRead(false);
		this.setReqDetail(msg.getReqDetail());
		this.setReqItem(msg.getReqItem());
		this.setReqItemID(msg.getReqItemID());
		this.setStartTime(msg.getStartTime());
	}

	public MySHelpMeMsg() {
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
