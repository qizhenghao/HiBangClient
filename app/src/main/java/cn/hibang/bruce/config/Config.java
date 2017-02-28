package cn.hibang.bruce.config;

import java.io.File;

import android.os.Environment;

public class Config {   
 
//	public static final String SERVER_IP = "192.168.191.1"; 
	public static final String SERVER_IP = "123.207.167.204";
	
	public static final String MSG_PACKAGE_NAME = "cn.hibang.huxing.clientmessage";
	
	public static final String PACKAGE_ENCODE_PHOTO = "ISO-8859-1";
	public static final String PACKAGE_ENCODE = "UTF-8";
	
	public static final File SDCARD_DIRECTORY = Environment.getExternalStorageDirectory();
	public static final String CACH_PATH = SDCARD_DIRECTORY.getPath() + "/HiBangMedia/photo/";
	public static final String PHOTO_FORMAT = ".jpg";
	
	public static final int SERVER_PORT = 9999;
	
	public static final int DB_REQUIRE_MAX = 50;
	
	public static final int DB_MESSAGE_MAX = 50;
	
	public static final int DB_CHAT_MAX = 500;
	
	public static final int DB_RECENT_PUB_MAX = 50;
	
	public static final int DB_FRIEND_MAX = 100;
	
	public static final int DB_REQUIER_SIZE = 10;
	
	public static final String TAG_LoginActivity = "LoginActivity";
	public static final String TAG_RegisterActivity = "RegisterActivity";
	
	
	public static final String TAG_MainTabActivity = "MainTabActivity";
	public static final String TAG_FriendActivity = "FriendActivity";
	public static final String TAG_MessageActivity = "MessageActivity";
	public static final String TAG_PublishActivity = "PublishActivity";
	public static final String TAG_RecommendActivity = "RecommendActivity";
	public static final String TAG_UserSettingActivity = "UserSettingActivity";
	public static final String TAG_ChatActivity = "ChatActivity";
	public static final String TAG_MyFriendFragment = "MyFriendFragment";
	public static final String TAG_InformationActivity = "InformationActivity";
	public static final String TAG_ChatFriendInfoActivity = "ChatFriendInfoActivity";
	
	public static final int TAG_HelpMeMessage = 1;
	public static final int TAG_MeHelpMessage = 2;
	public static final int TAG_SOrderMsg = 3;
	public static final int TAG_RecomMessage = 4;
	
	
	
	
}
