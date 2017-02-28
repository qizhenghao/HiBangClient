package cn.hibang.bruce.domain;

import cn.hibang.huxing.clientmessage.CPubMeHelpMsg;

public class MyPubMeHelp extends CPubMeHelpMsg{
	private Integer id ;
	private String reqItemName;
	
	public MyPubMeHelp() {}

	public MyPubMeHelp(CPubMeHelpMsg msg, String name) {
		this.setHelpUserID(msg.getHelpUserID());
		this.setReqItemID(msg.getReqItemID());
		this.setReqItemName(name);
		this.setStartTime(msg.getStartTime());
		this.setEndTime(msg.getEndTime());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReqItemName() {
		return reqItemName;
	}

	public void setReqItemName(String reqItemName) {
		this.reqItemName = reqItemName;
	}

}
