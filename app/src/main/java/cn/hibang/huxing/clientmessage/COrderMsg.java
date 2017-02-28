package cn.hibang.huxing.clientmessage;


import org.json.JSONException;
import org.json.JSONObject;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.msgutility.OrderMsgType;

import com.fasterxml.jackson.core.JsonProcessingException;


public class COrderMsg extends IMessage{

	private static final long serialVersionUID = -5005338824262617444L;

	private Integer helpme_id;
	private OrderMsgType orderType;
	
	/* The following fields works while orderType equals OrderMsgType.REQUEST */
	private Integer helpID; /* 用户ID */
	private Integer beHelpedID;/* 用户ID */
	private Integer reqItemID;
	
	private String startTime;
	private String endTime;
	private String reqDetail;

	/* The following fields works while orderType equals OrderMsgType.COMMIT */
	private boolean bOrdered;
	
	
	public COrderMsg() {
		super();
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
	public boolean isbOrdered() {
		return bOrdered;
	}
	public void setbOrdered(boolean bOrdered) {
		this.bOrdered = bOrdered;
	}
	public Integer getHelpme_id() {
		return helpme_id;
	}
	public void setHelpme_id(Integer helpme_id) {
		this.helpme_id = helpme_id;
	}
	public OrderMsgType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderMsgType orderType) {
		this.orderType = orderType;
	}
	
}
