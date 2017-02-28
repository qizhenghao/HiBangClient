package cn.hibang.liuzhiwei.message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.newhaibang.R;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.SUserInfoRequestListener;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.huxing.clientmessage.CUserInfoRequest;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.FriendState;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.servermessage.SOrderMsg;
import cn.hibang.huxing.servermessage.SUserInfoRequest;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

public class ChatFriendInfoActivity extends BaseActivity implements
		OnClickListener, SUserInfoRequestListener {

	private HeaderLayout mHeaderLayout;
	private LinearLayout mIvBack;
	private Button mEditBtn;
	private BaseApplication mApplication;
	private HiBangUser user;

	private ImageView mAvatar;// 头像
	private HandyTextView mHtvName;// 姓名
	private LinearLayout mLayoutGender;// 性别根布局
	private ImageView mIvGender;// 性别
	private HandyTextView mHtvAge;// 年龄
	private HandyTextView mHtvSchool;// 学校
	private EmoticonsTextView mEtvAccount;// 嗨帮账号
	private EmoticonsTextView mEtvSign;// 个性签名
	private EmoticonsTextView mEtvMail;// 邮箱
	private EmoticonsTextView mEtvPhone;// 电话

	private HiBangUser friendUser;
	private BaseApplication application;
	private int friendId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_friend_info);
		SMsgManage.getManager().setsUserInfoRequestListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.contextMap.put(Config.TAG_ChatFriendInfoActivity, this);
		application = (BaseApplication) getApplication();
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void onResume() {
		SMsgManage.getManager().setsUserInfoRequestListener(this);
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}

	@Override
	protected void initViews() {

		mHeaderLayout = (HeaderLayout) findViewById(R.id.chating_friend_info_header);
		mHeaderLayout.myBackSettingTitle("聊天");
		mHeaderLayout.mySettingMidTitle("好友资料");

		mIvBack = (LinearLayout) findViewById(R.id.header_iv_setting_back);

		mAvatar = (ImageView) findViewById(R.id.chating_user_iv_avatar);
		mHtvName = (HandyTextView) findViewById(R.id.chating_user_htv_name);
		mLayoutGender = (LinearLayout) findViewById(R.id.chating_user_layout_gender);
		mIvGender = (ImageView) findViewById(R.id.chating_user_iv_gender);
		mHtvAge = (HandyTextView) findViewById(R.id.chating_user_htv_age);
		mHtvSchool = (HandyTextView) findViewById(R.id.chating_user_htv_school);
		mEtvAccount = (EmoticonsTextView) findViewById(R.id.chating_user_info_account);
		mEtvSign = (EmoticonsTextView) findViewById(R.id.chating_user_info_own_sign);
		mEtvMail = (EmoticonsTextView) findViewById(R.id.chating_user_info_mail);
		mEtvPhone = (EmoticonsTextView) findViewById(R.id.chating_user_info_phonenum);
	}

	@Override
	protected void initEvents() {

		mIvBack.setOnClickListener(this);

	}

	private void init() {

		Bundle bundle = null;
		bundle = this.getIntent().getExtras();

		friendId = bundle.getInt("friendId");
		// 从数据库中获取头像
		String path = DBManage.getPhotoPathById(friendId);
		if (path == null || path.equals("")) {
			mAvatar.setImageResource(R.drawable.my_test_chating_image);
			CPhotoRequestMsg msg = new CPhotoRequestMsg();
			msg.setUserID(friendUser.getUserID());
			mApplication.client.sendMessage(msg);
		} else {
			try {
				mAvatar.setImageBitmap(BitmapFactory
						.decodeStream(new FileInputStream(path)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// 根据ID从数据库中获取hibangUser
		if (friendUser == null) {
			CUserInfoRequest msg = new CUserInfoRequest();
			msg.setUserId(friendId);
			application.client.sendMessage(msg);
			return;
		}

		mHtvName.setText(friendUser.getUsername());
		if (friendUser.getGender() == GENDER.FEMALE) {
			mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
			mIvGender.setImageResource(R.drawable.ic_user_famale);
		} else {
			mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
			mIvGender.setImageResource(R.drawable.ic_user_male);
		}

		mHtvAge.setText(friendUser.getUserAge().toString());
		mHtvSchool.setText(friendUser.getSchool());
		mEtvAccount.setText(friendUser.getUserID().toString());
		mEtvSign.setText(friendUser.getPs());
		mEtvMail.setText(friendUser.getEmail());
		mEtvPhone.setText(friendUser.getPhone());

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.header_iv_setting_back:
			this.finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onUserInfoReqMsg(SUserInfoRequest request) {
		Message msg = new Message();
		msg.obj = request;
		handler.sendMessage(msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.KEYCODE_BACK == keyCode) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			friendUser = ((SUserInfoRequest) msg.obj).getUser();
			init();
		}
	};

}
