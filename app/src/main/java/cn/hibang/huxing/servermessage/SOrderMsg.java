package cn.hibang.huxing.servermessage;


import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.OrderMsgType;

public class SOrderMsg extends IMessage {
	
	private static final long serialVersionUID = -7055098592648093673L;
	
	private OrderMsgType orderType;
	private Integer helpme_id;
	
	/* The following fields works while orderType equals OrderMsgType.REQUEST*/
	private Integer helpID;
	private Integer beHelpedID;
	private String startTime;
	private String endTime;
	private String reqDetail;
	private Integer reqItemID;
	
	/* The following fields works while orderType equals OrderMsgType.COMMIT */
	private boolean bOrdered;
	
	public Integer getHelpme_id() {
		return helpme_id;
	}

	public void setHelpme_id(Integer helpme_id) {
		this.helpme_id = helpme_id;
	}

	
	
	@Override
	public String toString() {
		
		return null;
	}

	public OrderMsgType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderMsgType orderType) {
		this.orderType = orderType;
	}

	public Integer getHelpID() {
		return helpID;
	}

	public void setHelpID(Integer helpID) {
		this.helpID = helpID;
	}

	public Integer getBeHelpedID() {
		return beHelpedID;
	}

	public void setBeHelpedID(Integer beHelpedID) {
		this.beHelpedID = beHelpedID;
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

	public String getReqDetail() {
		return reqDetail;
	}

	public void setReqDetail(String reqDetail) {
		this.reqDetail = reqDetail;
	}

	public Integer getReqItemID() {
		return reqItemID;
	}

	public void setReqItemID(Integer reqItemID) {
		this.reqItemID = reqItemID;
	}

	public boolean isbOrdered() {
		return bOrdered;
	}

	public void setbOrdered(boolean bOrdered) {
		this.bOrdered = bOrdered;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
