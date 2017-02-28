package cn.hibang.bruce.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.MessageEvent;

import com.example.newhaibang.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.domain.MyPhoto;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.bruce.interf.MyChattingListener;
import cn.hibang.bruce.interf.MyMainTabListener;
import cn.hibang.bruce.interf.SChatMessageListener;
import cn.hibang.bruce.interf.SFriendListListener;
import cn.hibang.bruce.interf.SHelpMeMsgListener;
import cn.hibang.bruce.interf.SLoginMsgListener;
import cn.hibang.bruce.interf.SMeHelpMsgListener;
import cn.hibang.bruce.interf.SOrderMsgListener;
import cn.hibang.bruce.interf.SPhotoRequestMsgListener;
import cn.hibang.bruce.interf.SQueryResultMsgListener;
import cn.hibang.bruce.interf.SRecommendListMsgListener;
import cn.hibang.bruce.interf.SRegisterMsgListener;
import cn.hibang.bruce.interf.SUserInfoRequestListener;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.msgutility.OrderMsgType;
import cn.hibang.huxing.servermessage.SChatMessage;
import cn.hibang.huxing.servermessage.SFriendList;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.huxing.servermessage.SOrderMsg;
import cn.hibang.huxing.servermessage.SPhotoRequestMsg;
import cn.hibang.huxing.servermessage.SSelectReqMsg;
import cn.hibang.huxing.servermessage.SRecommendListMsg;
import cn.hibang.huxing.servermessage.SRegisterMsg;
import cn.hibang.huxing.servermessage.SUserInfoRequest;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.maintabs.InformationActivity;
import cn.hibang.liuzhiwei.maintabs.MainTabActivity;
import cn.hibang.liuzhiwei.maintabs.MessageActivity;
import cn.hibang.liuzhiwei.maintabs.RecommendActivity;
import cn.hibang.liuzhiwei.message.ChatActivity;

public class SMsgManage {
	private static Context currContext = null;

	public static Context getCurrContext() {
		return currContext;
	}

	private static int chattingUserId = 0;

	public static int getChattingUserId() {
		return chattingUserId;
	}

	public void setChattingUserId(int chattingUserId) {
		SMsgManage.chattingUserId = chattingUserId;
	}

	private static SLoginMsgListener sLoginMsgListener = null;
	private static SRecommendListMsgListener sRlMsgListener = null;
	private static SChatMessageListener sChatMessageListener = null;
	private static SFriendListListener sFriendListListener = null;
	private static SHelpMeMsgListener sHelpMeMsgListener = null;
	private static SMeHelpMsgListener sMeHelpMsgListener = null;
	// private static SNotificationMsgListener sNotificationMsgListener = null;
	// wait to code
	private static SPhotoRequestMsgListener sPhotoRequestMsgListener = null;
	private static SQueryResultMsgListener sQueryResultMsgListener = null;
	private static SRegisterMsgListener sRegisterMsgListener = null;

	private static MyChattingListener myChattingListener = null;
	private static MyMainTabListener myMainTabListener = null;
	
	private static SUserInfoRequestListener sUserInfoRequestListener = null;
	private static SOrderMsgListener sOrderMsgListener = null;
	
	

	public void setsOrderMsgListener(SOrderMsgListener sOrderMsgListener) {
		SMsgManage.sOrderMsgListener = sOrderMsgListener;
	}

	public void setsUserInfoRequestListener(
			SUserInfoRequestListener sUserInfoRequestListener) {
		SMsgManage.sUserInfoRequestListener = sUserInfoRequestListener;
	}

	public void setMyMainTabListener(MyMainTabListener myMainTabListener) {
		SMsgManage.myMainTabListener = myMainTabListener;
	}

	public void setMyChattingListener(MyChattingListener myChattingListener) {
		SMsgManage.myChattingListener = myChattingListener;
	}

	public static void messageReceived(MessageEvent e) {
		String msgName = e.getMessage().getClass().getName();
		if (msgName.equals(SLoginMsg.class.getName())) {
			sendToLogin(e);
		} else if (msgName.equals(SRecommendListMsg.class.getName())) {
			sendToRecommend(e);
		} else if (msgName.equals(SHelpMeMsg.class.getName())) {
			sendToHelpMeMsg(e);
		} else if (msgName.equals(SMeHelpMsg.class.getName())) {
			sendToMeHelpMsg(e);
		} else if (msgName.equals(SFriendList.class.getName())) {
			sendToFriend(e);
		} else if (msgName.equals(SChatMessage.class.getName())) {
			sendToChat(e);
		} else if (msgName.equals(SSelectReqMsg.class.getName())) {
			sendToQuery(e);
		} else if (msgName.equals(SRegisterMsg.class.getName())) {
			sendToRegister(e);
		} else if (msgName.equals(SPhotoRequestMsg.class.getName())) {
			sendToPhoto(e);
		} else if (msgName.equals(SUserInfoRequest.class.getName())) {
			sendToInformation(e);
		} else if(msgName.equals(SOrderMsg.class.getName())) {
			sendToInformation1(e);
		} 
	}

	private static void sendToInformation1(MessageEvent e) {
		SOrderMsg msg = (SOrderMsg) e.getMessage();
		if (isCurrUI(Config.TAG_InformationActivity)) {
			sOrderMsgListener.onSOrderMsgReceived(msg);
		} else {
			Intent intent = new Intent(currContext, InformationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("TAG", Config.TAG_SOrderMsg);
			bundle.putSerializable("SOrderMsg", msg);
			intent.putExtras(bundle);
			String content = "";
			String title  = msg.getReqDetail();
				if(msg.getOrderType() == OrderMsgType.REQUEST) {
					content = "嗨，有人帮助你啦，快来看吧！";
				} else {
					if(msg.isbOrdered()) {
						content = "嗨，你们已经正在交易中了";
					}
				}
			myNotifi(1000,intent, title, content);
		}
	}

	private static void sendToInformation(MessageEvent e) {
		SUserInfoRequest msg = (SUserInfoRequest) e.getMessage();
		if (isCurrUI(Config.TAG_InformationActivity)) {
			sUserInfoRequestListener.onUserInfoReqMsg(msg);
		} else if(isCurrUI(Config.TAG_ChatFriendInfoActivity)) {
			sUserInfoRequestListener.onUserInfoReqMsg(msg);
		}
		
	}

	private static void sendToPhoto(MessageEvent e) {
		SPhotoRequestMsg msg = (SPhotoRequestMsg) e.getMessage();
		String photoName = String.valueOf(msg.getUserId());
		// wait to write
		File photoFile = new File(Config.CACH_PATH);
		if (!photoFile.exists()) {
			photoFile.mkdirs();
		}
		String url = Config.CACH_PATH + photoName;
		File tempFile = new File(url);
		byte[] b = msg.getPhoto();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(tempFile);
			fos.write(b, 0, b.length);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		DBManage.addPhoto(new MyPhoto(msg.getUserId(), url));
		sPhotoRequestMsgListener.onSPhotoReqMsgReveived();
	}

	public static void sendToRegister(MessageEvent e) {
		if (isCurrUI(Config.TAG_RegisterActivity)) {
			SRegisterMsg msg = (SRegisterMsg) e.getMessage();
			sRegisterMsgListener.onMsgReveived(msg);
		} 
	}

	public static void sendToQuery(MessageEvent e) {
		SSelectReqMsg msg = (SSelectReqMsg) e.getMessage();
		DBManage.addRequirement(msg.getUserReqList(), true);
		if (isCurrUI(Config.TAG_RecommendActivity)) {
			sQueryResultMsgListener.onQueryResultMsg(msg);
		}
	}

	public static void sendToMeHelpMsg(MessageEvent e) {
		SMeHelpMsg msg = (SMeHelpMsg) e.getMessage();
		for(SMeHelpMsg m : DataHiBang.sMeHelpMsgList) {
			if(m.getHelpUserID() == msg.getHelpUserID()) {
				return;
			}
		}
		synchronized (String.class) {
			if(DataHiBang.sMeHelpMsgList.size()>=10) {
				DataHiBang.sMeHelpMsgList.remove(0);
			}
			
			DataHiBang.sMeHelpMsgList.add(msg);
		}
		
//		DBManage.addMeHelpMsg(msg, true);
		if (isCurrUI(Config.TAG_MessageActivity)) {
			sMeHelpMsgListener.onMeHelpMsgReveived(msg);
		} else {
			myMainTabListener.onMsgReveived(msg);
			Intent intent = new Intent(currContext, InformationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("TAG", Config.TAG_MeHelpMessage);
			MySMeHelpMsg meHelpMsg = new MySMeHelpMsg(msg);
			bundle.putSerializable("meHelpMessage", meHelpMsg);
			intent.putExtras(bundle);
			myNotifi(1001,intent, msg.getHelpName(), msg.getReqItem());
		}
	}

	public static void sendToHelpMeMsg(MessageEvent e) {
		SHelpMeMsg msg = (SHelpMeMsg) e.getMessage();
		synchronized (String.class) {
		if(DataHiBang.sHelpMeMsgList.size()>=10) {
			DataHiBang.sHelpMeMsgList.remove(0);
		}
		DataHiBang.sHelpMeMsgList.add(msg);
		}
		if (isCurrUI(Config.TAG_MessageActivity)) {
			sHelpMeMsgListener.onHelpMeMsgReveived(msg);
		} else {
//			Intent intent = new Intent(currContext, MessageActivity.class);
			myMainTabListener.onMsgReveived(msg);
			Intent intent = new Intent(currContext, InformationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("TAG", Config.TAG_HelpMeMessage);
			MySHelpMeMsg helpMeMsg = new MySHelpMeMsg(msg);
			bundle.putSerializable("helpMeMessage", helpMeMsg);
			
			intent.putExtras(bundle);
			myNotifi(1002,intent, msg.getHelpName(), msg.getReqItem());
		}
	}

	public static void sendToFriend(MessageEvent e) {
		SFriendList msg = (SFriendList) e.getMessage();
		List<HiBangUser> list = msg.getFriendList();
		if (list.size() >= 1) {
			DBManage.addFriendList(list);
			Log.i("sMsgManage", list.toString());
			System.out.println(list.toString());
			if (isCurrUI(Config.TAG_FriendActivity)) {
				sFriendListListener.onMsgReveived(list);
			}
		}
	}

	public static void sendToChat(MessageEvent e) {
		SChatMessage msg = (SChatMessage) e.getMessage();
		if (msg.getSenderID() == chattingUserId) {
			DBManage.addSChatMsg(msg, false);
			myChattingListener.onMsgReceived(msg);
		} else {
			DBManage.addSChatMsg(msg, true);
			Intent intent = new Intent(currContext, ChatActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("friendId", msg.getSenderID());
			bundle.putString("friendName", msg.getReceiverName());
			intent.putExtras(bundle);
			String title = "嗨帮-聊天消息 (共1条未读)";
			myNotifi(1003,intent,title,msg.getChatContent());
			
			// sChatMessageListener.onMsgReveived(msg);
		}
	}

	private static void myNotifi(int index, Intent intent, String title, String content) {
		PendingIntent pi = PendingIntent.getActivity(currContext, 0,
				intent, 0);
		Notification noti = new Notification.Builder(currContext)
				.setAutoCancel(true)
				.setContentTitle(title)
				.setContentText("   " + content)
				.setSmallIcon(R.drawable.ic_launcher_logo)
				.setTicker("嗨帮: " + content)
				// .setLargeIcon(new Bitmap())
				.setContentIntent(pi).build();
		NotificationManager nm =  (NotificationManager) currContext.getSystemService(currContext.NOTIFICATION_SERVICE);
		nm.notify(index,noti);
	}

	public static void sendToLogin(MessageEvent e) {
		SLoginMsg msg = (SLoginMsg) e.getMessage();
		sLoginMsgListener.onSLoginMsgReveived(msg);
	}

	public static void sendToRecommend(MessageEvent e) {
		SRecommendListMsg msg = (SRecommendListMsg) e.getMessage();
//		DBManage.addRequirement(msg.getRecommendList(), true);
		synchronized (String.class) {
			if(DataHiBang.sRecommMsgList.size()>25) {
				int n = DataHiBang.sRecommMsgList.size() - 25;
				for(int i=0;i<n;i++) {
					DataHiBang.sRecommMsgList.remove(i);
				}
			}
			DataHiBang.sRecommMsgList.addAll(msg.getRecommendList());
		}
		
		if (isCurrUI(Config.TAG_RecommendActivity)) {
			sRlMsgListener.onMsgReveived(msg);
		} else {
			Intent intent = new Intent(currContext, MainTabActivity.class);
			myNotifi(1004,intent, "快来帮助别人吧······", msg.getRecommendList().get(0).getReqDetail());
		}
	}

	private static SMsgManage sMsgManage = null;

	public static Map<String, Context> contextMap = null;

	public static SMsgManage getManager() {
		if (sMsgManage == null) {
			synchronized (SMsgManage.class) {
				if (sMsgManage == null) {
					sMsgManage = new SMsgManage();
					contextMap = new HashMap<String, Context>();
				}
			}
		}
		return sMsgManage;
	}

	public static boolean isCurrUI(String msgName) {
		if (currContext == contextMap.get(msgName)) {
			return true;
		}
		return false;
	}

	public void setCurrContext(Context currContext) {
		SMsgManage.currContext = currContext;
	}

	public void setsLoginMsgListener(SLoginMsgListener sLoginMsgListener) {
		SMsgManage.sLoginMsgListener = sLoginMsgListener;
	}

	public void setsRlMsgListener(SRecommendListMsgListener sRlMsgListener) {
		SMsgManage.sRlMsgListener = sRlMsgListener;
	}

	public static void setChatMessageListener(
			SChatMessageListener chatMessageListener) {
		SMsgManage.sChatMessageListener = chatMessageListener;
	}

	public void setFriendListListener(SFriendListListener friendListListener) {
		SMsgManage.sFriendListListener = friendListListener;
	}

	public void setSHelpMeMsgListener(SHelpMeMsgListener helpMeMsgListener) {
		SMsgManage.sHelpMeMsgListener = helpMeMsgListener;
	}

	public void setsMeHelpMsgListener(SMeHelpMsgListener sMeHelpMsgListener) {
		SMsgManage.sMeHelpMsgListener = sMeHelpMsgListener;
	}

	// public static void setsNotificationMsgListener(
	// SNotificationMsgListener sNotificationMsgListener) {
	// SMsgManage.sNotificationMsgListener = sNotificationMsgListener;
	// }

	public void setsPhotoRequestMsgListener(
			SPhotoRequestMsgListener sPhotoRequestMsgListener) {
		SMsgManage.sPhotoRequestMsgListener = sPhotoRequestMsgListener;
	}

	public static void setsQueryResultMsgListener(
			SQueryResultMsgListener sQueryResultMsgListener) {
		SMsgManage.sQueryResultMsgListener = sQueryResultMsgListener;
	}

	public void setsRegisterMsgListener(
			SRegisterMsgListener sRegisterMsgListener) {
		SMsgManage.sRegisterMsgListener = sRegisterMsgListener;
	}

	public static void setsMsgManage(SMsgManage sMsgManage) {
		SMsgManage.sMsgManage = sMsgManage;
	}

}
