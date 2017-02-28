package cn.hibang.bruce.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.netty.HiBangClient;
import cn.hibang.bruce.service.NettyService;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liaohongxian.activity.Welcome;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.testentity.TestFriend;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class BaseApplication extends Application {

	public List<Activity> activityList = null;
	private Activity currentActi = null;
	public HiBangClient client = null;
	private HiBangUser user = null;
	private HiBang hiBang = null;
	private NettyService nettyService;
	private boolean isBind = false;
	// 好友列表相关
	public List<TestFriend> mFriendList = new ArrayList<TestFriend>();
	// 表情图片相关
	public static List<String> mEmoticons = new ArrayList<String>();
	public static Map<String, Integer> mEmoticonsId = new HashMap<String, Integer>();
	public static List<String> mEmoticons_Zem = new ArrayList<String>();
	public static List<String> mEmoticons_Zemoji = new ArrayList<String>();

	public static List<Activity> listActivity = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		activityList = new ArrayList<Activity>();
		user = new HiBangUser();
		DBManage.context = getApplicationContext();
		setHiBang(new HiBang(this));
		client = new HiBangClient();
			
		isBind = bindService(new Intent(getApplicationContext(), NettyService.class),
				connection, Context.BIND_AUTO_CREATE);
//		nettyService.connectServer();
//		startService(new Intent(getApplicationContext(), NettyService.class));

//		new Thread(mTasks).start();
		init();
		super.onCreate();
	}
	
	
	private void init() {
		for (int i = 1; i < 64; i++) {
			String emoticonsName = "[zem" + i + "]";
			int emoticonsId = getResources().getIdentifier("zem" + i,
					"drawable", getPackageName());
			mEmoticons.add(emoticonsName);
			mEmoticons_Zem.add(emoticonsName);
			mEmoticonsId.put(emoticonsName, emoticonsId);
		}
		for (int i = 1; i < 59; i++) {
			String emoticonsName = "[zemoji" + i + "]";
			int emoticonsId = getResources().getIdentifier("zemoji_e" + i,
					"drawable", getPackageName());
			mEmoticons.add(emoticonsName);
			mEmoticons_Zemoji.add(emoticonsName);
			mEmoticonsId.put(emoticonsName, emoticonsId);
		}
	}

	private Runnable mTasks = new Runnable() {

		public void run() {
			client.start(Config.SERVER_IP, Config.SERVER_PORT);
			init();
			Log.i(getClass().getName(), "client.start");
		}
	};

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public Activity getCurrentActi() {
		return currentActi;
	}

	public void setCurrentActi(Activity currentActi) {
		this.currentActi = currentActi;
	}

	public void setUser(HiBangUser user2) {
		user = user2;
		DBManage.myId = user.getUserID();
	}

	public HiBangUser getUser() {
		return user;
	}

	public HiBang getHiBang() {
		return hiBang;
	}

	public void setHiBang(HiBang hiBang) {
		this.hiBang = hiBang;
	}

	public void clearActivity() {
//		client.stop();
		unbindService(connection);
		for (Activity a : listActivity) {
			a.finish();
		}
	}

	public void addActiv(Activity a) {
		listActivity.add(a);
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name,
				IBinder service) {
			nettyService = ((NettyService.NettyBinder) service)
					.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			nettyService = null;
		}
	};
}
