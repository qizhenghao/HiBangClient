package cn.hibang.liaohongxian.dao;

import java.util.List;

import android.content.Context;

import cn.hibang.bruce.domain.MyChatMsg;
import cn.hibang.bruce.domain.MyPhoto;
import cn.hibang.bruce.domain.MyPubHelpMe;
import cn.hibang.bruce.domain.MyPubMeHelp;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.huxing.clientmessage.CChatMessage;
import cn.hibang.huxing.msgutility.FriendState;
import cn.hibang.huxing.msgutility.HelpRelation;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.msgutility.UserRequirement;
import cn.hibang.huxing.servermessage.SChatMessage;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.liaohongxian.dao.FriendDB;
import cn.hibang.liaohongxian.dao.HelpMeMsgDB;
import cn.hibang.liaohongxian.dao.MeHelpMsgDB;
import cn.hibang.liaohongxian.dao.PhotoDB;
import cn.hibang.liaohongxian.dao.PubHelpMeDB;
import cn.hibang.liaohongxian.dao.PubMeHelpDB;
import cn.hibang.liaohongxian.dao.UserRequirmentDB;

public class DBManage {

	public static Context context = null;
	public static int myId = 0;
	

	public int getMyId() {
		return myId;
	}

	public static void addRequirement(List<UserRequirement> list, boolean isNoRead) {
		UserRequirmentDB.newInstance(context).addRequirement(list, isNoRead);
	}

	public static MyUserRequire getReqireById(int id) {
		return UserRequirmentDB.newInstance(context).getReqireById(id);
	}

	public static void deleteRequireById(int id) {
		UserRequirmentDB.newInstance(context).deleteRequireById(id);

	}

	public static List<MyUserRequire> getRequireByCount(int count) {

		return UserRequirmentDB.newInstance(context).getRequireByCount(count);
	}

	public static void deleteRequireMax(int max) {
		UserRequirmentDB.newInstance(context).deleteRequireMax(max);

	}

	public static void addMeHelpMsg(SMeHelpMsg msg, boolean isNoRead) {
		MeHelpMsgDB.newInstance(context).addMeHelpMsg(msg, isNoRead);

	}

	public static MySMeHelpMsg getMeHelpMsgById(int id) {

		return MeHelpMsgDB.newInstance(context).getMeHelpMsgById(id);
	}

	public static List<MySMeHelpMsg> getMeHelpMsgByCount(int count) {

		return MeHelpMsgDB.newInstance(context).getMeHelpMsgByCount(count);
	}

	public static void deleteMeHelpMsgById(int id) {
		MeHelpMsgDB.newInstance(context).deleteMeHelpMsgById(id);

	}

	public static void deleteMeHelpMsgMax(int max) {
		MeHelpMsgDB.newInstance(context).deleteMeHelpMsgMax(max);

	}

	public static void addHelpMeMsg(SHelpMeMsg msg, boolean isNoRead) {
		HelpMeMsgDB.newInstance(context).addHelpMeMsg(msg, isNoRead);

	}

	public static MySHelpMeMsg getHelpMeMsgById(int id) {

		return HelpMeMsgDB.newInstance(context).getHelpMeMsgById(id);
	}

	public static List<MySHelpMeMsg> getHelpMeMsgByCount(int count) {

		return HelpMeMsgDB.newInstance(context).getHelpMeMsgByCount(count);
	}

	public static void deleteHelpMeMsgById(int id) {
		HelpMeMsgDB.newInstance(context).deleteHelpMeMsgById(id);

	}

	public static void deleteHelpMeMsgMax(int max) {
		HelpMeMsgDB.newInstance(context).deleteHelpMeMsgMax(max);

	}

	public static void addCPubMeHelp(MyPubMeHelp msg) {
		PubMeHelpDB.newInstance(context).addCPubMeHelp(msg);

	}

	public static List<MyPubMeHelp> getAllCPubMeHelp() {

		return PubMeHelpDB.newInstance(context).getAllCPubMeHelp();
	}

	public static MyPubMeHelp getCPubMeHelpById(int id) {
		return PubMeHelpDB.newInstance(context).getCPubMeHelpById(id);
	}

	public static void deleteCPubMeHelp(int id) {
		PubMeHelpDB.newInstance(context).deleteCPubMeHelp(id);

	}

	public static void addCPubHelpMe(MyPubHelpMe msg) {
		PubHelpMeDB.newInstance(context).addCPubHelpMe(msg);

	}

	public static List<MyPubHelpMe> getAllCPubHelpMe() {

		return PubHelpMeDB.newInstance(context).getAllCPubHelpMe();
	}

	public static MyPubHelpMe getCPubHelpMeById(int id) {
		return PubHelpMeDB.newInstance(context).getCPubHelpMeById(id);
	}

	public static void deleteCPubHelpMe(int id) {
		PubHelpMeDB.newInstance(context).deleteCPubHelpMe(id);
	}

	public static void addFriendList(List<HiBangUser> list) {
		FriendDB.newInstance(context).addFriendList(list);

	}

	public static void addFriend(HiBangUser user) {
		FriendDB.newInstance(context).addFriend(user);

	}

	public static void deleteFriendById(int friendId) {
		FriendDB.newInstance(context).deleteFriendById(friendId);

	}

	public static void changeFriendStatusById(int friendId, FriendState state) {
		FriendDB.newInstance(context).changeFriendStatusById(friendId, state);

	}

	public static HiBangUser getFriendById(int friendId) {

		return FriendDB.newInstance(context).getFriendById(friendId);
	}

	public static List<HiBangUser> getFriendByCount(int count,FriendState friendState,HelpRelation helpRelation) {

		return FriendDB.newInstance(context).getFriendByCount(count, friendState, helpRelation);
	}

	public static void addSChatMsg(SChatMessage msg, boolean noRead) {
//		ChatMsgDB.newInstance(context).addChatMsg(msg, noRead);
		ChatMsgDB.newInstance(context).addSChatMsg(msg, noRead);

	}
	
	public static void addCChatMsg(CChatMessage msg) {
		ChatMsgDB.newInstance(context).addCChatMsg(msg);
		
	}

	public static void deleteChatMsgById(int id) {
		ChatMsgDB.newInstance(context).deleteChatMsgById(id);

	}

	public static void deleteChatMsgByFriendId(int friendId) {
		ChatMsgDB.newInstance(context).deleteChatMsgByFriendId(friendId);

	}

	public static MyChatMsg getChatMsgById(int id) {

		return ChatMsgDB.newInstance(context).getChatMsgById(id);
	}

	public static List<MyChatMsg> getChatMsgByFriendId(int friendId, int count) {

		return ChatMsgDB.newInstance(context).getChatMsgByFriendId(friendId, count);
	}

	public static void addPhoto(MyPhoto photo) {
		PhotoDB.newInstance(context).addPhoto(photo);

	}

	public static void deletePhotoById(int friendId) {
		PhotoDB.newInstance(context).deletePhotoById(friendId);

	}

	public static void changePhotoById(int friendID,String newPath) {
		PhotoDB.newInstance(context).changePhotoById(friendID, newPath);

	}

	public static String getPhotoPathById(int friendId) {

		return PhotoDB.newInstance(context).getPhotoPathById(friendId);
	}
	
	

	public static int getRequireNoReadCount() {
		// TODO Auto-generated method stub
		return UserRequirmentDB.newInstance(context).getRequireNoReadCoun();
	}

	public static int getHelpMeMsgNoReadCount() {
		// TODO Auto-generated method stub
		return HelpMeMsgDB.newInstance(context).getHelpMeMsgNoReadCount();
	}

	public static int getMeHelpMsgNoReadCount() {
		// TODO Auto-generated method stub
		return MeHelpMsgDB.newInstance(context).getMeHelpMsgNoReadCount();
	}

	public static int getChatMsgNoReadCount() {
		// TODO Auto-generated method stub
		return ChatMsgDB.newInstance(context).getChatMsgNoReadCount();
	}

}
