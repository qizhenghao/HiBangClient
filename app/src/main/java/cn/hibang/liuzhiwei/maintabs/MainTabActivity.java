package cn.hibang.liuzhiwei.maintabs;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.MyMainTabListener;
import cn.hibang.bruce.interf.SLoginMsgListener;
import cn.hibang.bruce.service.NettyService;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.servermessage.SChatMessage;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.huxing.servermessage.SNotificationMsg;
import cn.hibang.huxing.servermessage.SRecommendListMsg;
import cn.hibang.liaohongxian.activity.LoginActivity;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.view.HandyTextView;

import com.example.newhaibang.R;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements MyMainTabListener,SLoginMsgListener{
	private TabHost mTabHost;
	private ImageView recMsgPoint = null;
	private ImageView msgMsgPoint = null;
	private ImageView friMsgPoint = null;
	private ImageView setMsgPoint = null;
	BaseApplication application = null;
	NetCheckReceiver checkReceiver = null;
	
	ViewGroup vg ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintabs);
		SMsgManage.getManager().setMyMainTabListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.getManager().setsLoginMsgListener(this);
		SMsgManage.contextMap.put(Config.TAG_MainTabActivity, this);
	
		application = (BaseApplication) getApplication();
		initViews();
		initTabs();
		init();
		
		checkReceiver = new NetCheckReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		this.registerReceiver(checkReceiver, filter);
		
	}

	private void init() {
//		Bundle bundle = this.getIntent().getExtras();
//		String className = bundle.getString("className");
//		if(className.equals("") || className==null) {
//		} else {
//			if(className.equals(MessageActivity.class.getName())) {
//				startActivity(new Intent(this,MessageActivity.class));
//			} else if(className.equals(FriendActivity.class.getName())) {
//				startActivity(new Intent(this,FriendActivity.class));
//			}
//		}
		
	}

	private void initViews() {
		mTabHost = getTabHost();
	}

	private void initTabs() {
		LayoutInflater inflater = LayoutInflater.from(MainTabActivity.this);

		View recomend = inflater.inflate(
				R.layout.common_bottombar_tab_nearby, null);
		recMsgPoint = (ImageView) recomend.findViewById(R.id.msg_noread_point);
		TabHost.TabSpec nearbyTabSpec = mTabHost.newTabSpec(
				RecommendActivity.class.getName()).setIndicator(recomend);
		nearbyTabSpec.setContent(new Intent(MainTabActivity.this,
				RecommendActivity.class));
		
		mTabHost.addTab(nearbyTabSpec);

		View message = inflater.inflate(
				R.layout.common_bottombar_tab_site, null);
		msgMsgPoint = (ImageView) message.findViewById(R.id.msg_noread_point);
		TabHost.TabSpec nearbyFeedsTabSpec = mTabHost.newTabSpec(
				MessageActivity.class.getName()).setIndicator(message);
		nearbyFeedsTabSpec.setContent(new Intent(MainTabActivity.this,
				MessageActivity.class));
		mTabHost.addTab(nearbyFeedsTabSpec);

		View publish = inflater.inflate(
				R.layout.common_bottombar_tab_chat, null);
		TabHost.TabSpec sessionListTabSpec = mTabHost.newTabSpec(
				PublishActivity.class.getName()).setIndicator(publish);
		sessionListTabSpec.setContent(new Intent(MainTabActivity.this,
				PublishActivity.class));
		mTabHost.addTab(sessionListTabSpec);

		View friend = inflater.inflate(
				R.layout.common_bottombar_tab_friend, null);
		friMsgPoint = (ImageView) friend.findViewById(R.id.msg_noread_point);
		TabHost.TabSpec contactTabSpec = mTabHost.newTabSpec(
				FriendActivity.class.getName()).setIndicator(friend);
		contactTabSpec.setContent(new Intent(MainTabActivity.this,
				FriendActivity.class));
		mTabHost.addTab(contactTabSpec);

		View setting = inflater.inflate(
				R.layout.common_bottombar_tab_profile, null);
		setMsgPoint = (ImageView) setting.findViewById(R.id.msg_noread_point);
		TabHost.TabSpec userSettingTabSpec = mTabHost.newTabSpec(
				UserSettingActivity.class.getName()).setIndicator(
				setting);
		userSettingTabSpec.setContent(new Intent(MainTabActivity.this,
				UserSettingActivity.class));
		mTabHost.addTab(userSettingTabSpec);

	}
	
	private final int RECOM_MSG = 1;
	private final int MSG_MSG = 2;
	private final int FRIEND_MSG = 4;
	private final int SETTING_MSG = 5;
	private final int SLOGIN_MSG = 6;
	

	@Override
	public void onMsgReveived(IMessage iMessage) {
		String msgName = iMessage.getClass().getName();
		Message msg = new Message();
		if(msgName.equals(SRecommendListMsg.class.getName())) {
			int recomNoRead = DBManage.getRequireNoReadCount();
			msg.what = this.RECOM_MSG;
			msg.obj = recomNoRead;
			handler.sendMessage(msg);
		} else if(msgName.equals(SMeHelpMsg.class.getName()) || msgName.equals(SHelpMeMsg.class.getName())) {
			int msgNoRead = DBManage.getHelpMeMsgNoReadCount() + DBManage.getMeHelpMsgNoReadCount();
			msg.what = this.MSG_MSG;
			msg.obj = msgNoRead;
			handler.sendMessage(msg);
		} else if(msgName.equals(SChatMessage.class.getName())) {
			int chatNoRead = DBManage.getChatMsgNoReadCount();
			msg.what = this.FRIEND_MSG;
			msg.obj = chatNoRead;
			handler.sendMessage(msg);
		} else if(msgName.equals(SNotificationMsg.class.getName())) {
			
		}
		
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RECOM_MSG:
				int recomNoRead = (Integer) msg.obj;
				if(recomNoRead!=0) {
					recMsgPoint.setVisibility(View.VISIBLE);
				} else {
					recMsgPoint.setVisibility(View.INVISIBLE);
				}
			mTabHost.refreshDrawableState();
				break;
			case MSG_MSG:
				int msgNoRead = (Integer) msg.obj;
				if(msgNoRead!=0) {
					msgMsgPoint.setVisibility(View.VISIBLE);
				} else {
					msgMsgPoint.setVisibility(View.INVISIBLE);
				}
				mTabHost.refreshDrawableState();
				break;
			case FRIEND_MSG:
				int chatNoRead = (Integer) msg.obj;
				if(chatNoRead!=0) {
					friMsgPoint.setVisibility(View.VISIBLE);
				} else {
					friMsgPoint.setVisibility(View.INVISIBLE);
				}
				mTabHost.refreshDrawableState();
				break;
			case SETTING_MSG:
				int setNoRead = (Integer) msg.obj;
				if(setNoRead!=0) {
					setMsgPoint.setVisibility(View.VISIBLE);
				} else {
					setMsgPoint.setVisibility(View.INVISIBLE);
				}
				mTabHost.refreshDrawableState();
				break;
			case SLOGIN_MSG:
				HiBangUser user = ((SLoginMsg)msg.obj).getUser();
				if (user.getUserID() == -1) {
					showCustomToast("登录失败,账号或密码错误");
					Intent intent = new Intent(MainTabActivity.this,LoginActivity.class);
					MainTabActivity.this.startActivity(intent);
					MainTabActivity.this.finish();
				} else if(user.getUserID() == -2) { 
//					showCustomToast("登录失败,重复登录!");
				}
				else {
					showCustomToast("登陆成功!");
					application.setUser(user);
			}
		}
		}
	};
	
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		init();
		super.onResume();
	}

	@Override
	public void onSLoginMsgReveived(SLoginMsg sLoginMsg) {
		Message msg = new Message();
		msg.what = this.SLOGIN_MSG;
		msg.obj = sLoginMsg;
		handler.sendMessage(msg);
	}
	
	class NetCheckReceiver extends BroadcastReceiver {

		//android 中网络变化时所发的Intent的名字
	    private static final String netACTION="android.net.conn.CONNECTIVITY_CHANGE";
	    @Override
	    public void onReceive(Context context, Intent intent) {

	        if(intent.getAction().equals(netACTION)){
	    //Intent中ConnectivityManager.EXTRA_NO_CONNECTIVITY这个关键字表示着当前是否连接上了网络
	    //true 代表网络断开   false 代表网络没有断开
	            if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)){
	            	showCustomToast("网络抽风啦···");
	            }else{
	            	Intent restartCient = new Intent(MainTabActivity.this,NettyService.class);
	            	restartCient.setAction("RESTARTCIENT");
	                startService(restartCient);
	            }
	        }
	    }
}
}
