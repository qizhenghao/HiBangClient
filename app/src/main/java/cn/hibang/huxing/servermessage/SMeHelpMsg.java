package cn.hibang.huxing.servermessage;


import cn.hibang.huxing.clientmessage.IMessage;


public class SMeHelpMsg extends IMessage{

	private static final long serialVersionUID = 4539182584618876849L;
	private Integer helpme_id;
	
	private String beHelpedName;    /* 被帮助的用户名 */
	private Integer beHelpedUserID; /* 被帮助的用户ID */
	
	private String helpName;        /* 帮助的用户名 */
	private Integer helpUserID;     /* 帮助的用户ID */
	
	private String msgTime;           /* 消息投递时间 */
	private String startTime;         /* 开始时间 */
	private String endTime;           /* 结束时间 */
	
	private Integer reqItemID;      /* 需求ID */
	private String reqItem;         /* 需求名 */
	private String reqDetail;       /* 需求备注 */
	
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
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getHelpme_id() {
		return helpme_id;
	}



	public void setHelpme_id(Integer helpme_id) {
		this.helpme_id = helpme_id;
	}
}
