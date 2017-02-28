package cn.hibang.bruce.domain;

import cn.hibang.huxing.clientmessage.CPubHelpMeMsg;

public class MyPubHelpMe extends CPubHelpMeMsg{
	private Integer id ;
	private String reqItemName;

	public MyPubHelpMe(){}
	
	public MyPubHelpMe(CPubHelpMeMsg msg, String name) {
		this.setUserID(msg.getUserID());
		this.setReqItemID(msg.getReqItemID());
		this.setReqDetail(msg.getReqDetail());
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
