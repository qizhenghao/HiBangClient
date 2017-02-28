package cn.hibang.bruce.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hibang.bruce.interf.SQueryResultMsgListener;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.msgutility.UserRequirement;
import cn.hibang.huxing.servermessage.SChatMessage;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.huxing.servermessage.SNotificationMsg;
import cn.hibang.huxing.servermessage.SPhotoRequestMsg;
import cn.hibang.huxing.servermessage.SSelectReqMsg;

public class DataHiBang {
	
	// no login and register

	
	public static List<SChatMessage> sChatMsgList = new ArrayList<SChatMessage>();
	
	public static List<HiBangUser> sFriendList = new ArrayList<HiBangUser>();
	
	public static List<SHelpMeMsg>  sHelpMeMsgList = new ArrayList<SHelpMeMsg>();
	
	public static List<SMeHelpMsg> sMeHelpMsgList =  new ArrayList<SMeHelpMsg>();
	
	public static List<SNotificationMsg> snNotifiMsgList = new ArrayList<SNotificationMsg>();
	
	public static List<SPhotoRequestMsg> sPhotoReqMsgList = new ArrayList<SPhotoRequestMsg>();
	
	public static List<SSelectReqMsg> sQueryResMsgList = new ArrayList<SSelectReqMsg>();
	
	public static List<UserRequirement> sRecommMsgList = new ArrayList<UserRequirement>();
	

}
