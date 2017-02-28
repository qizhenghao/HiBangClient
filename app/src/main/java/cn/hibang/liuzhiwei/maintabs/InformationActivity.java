package cn.hibang.liuzhiwei.maintabs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.newhaibang.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.bruce.interf.SOrderMsgListener;
import cn.hibang.bruce.interf.SUserInfoRequestListener;
import cn.hibang.huxing.clientmessage.COrderMsg;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.huxing.clientmessage.CUserInfoRequest;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.msgutility.OrderMsgType;
import cn.hibang.huxing.servermessage.SOrderMsg;
import cn.hibang.huxing.servermessage.SUserInfoRequest;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.message.ChatActivity;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.HeaderLayout.HeaderStyle;

public class InformationActivity extends BaseActivity implements
		OnClickListener, SUserInfoRequestListener, SOrderMsgListener {

	private HeaderLayout mHeaderLayout;// 标题栏
	private ImageView mAvatar;// 头像
	private HandyTextView mHtvName;// 姓名
	private LinearLayout mLayoutGender;// 性别根布局
	private ImageView mIvGender;// 性别
	private HandyTextView mHtvAge;// 年龄
	private HandyTextView mHtvSign;//个性签名
	private EmoticonsTextView mEtvMsgTitle;// 消息标题
	private EmoticonsTextView mEtvMsgDate;// 消息日期
	private EmoticonsTextView mEtvMsgTime;// 消息时间段
	private HandyTextView mEtvMsgRemark;// 消息备注
	private LinearLayout mLayoutChat;// 对话按钮
	private LinearLayout mLayoutTrade;// 交易按钮
	private LinearLayout mLayoutReview;// 评论按钮
	
	private ImageView mIvVip1;
	private ImageView mIvVip2;
	private ImageView mIvVip3;
	private ImageView mIvStar1;
	private ImageView mIvStar2;
	private ImageView mIvStar3;

	private MySHelpMeMsg helpMeMsg;
	private MySMeHelpMsg meHelpMsg;
	private HiBangUser currentUser;

	private BaseApplication application;
	COrderMsg cOrderMsg = null;
	
	private int vipNum;
	private int starNum;

	// private ListView mLvList;//评论内容列表
	// private FeedProfileCommentsAdapter mAdapter;
	// private List<FeedComment> mComments = new ArrayList<FeedComment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_layout);
		SMsgManage.getManager().setsUserInfoRequestListener(this);
		SMsgManage.getManager().setsOrderMsgListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.contextMap.put(Config.TAG_InformationActivity, this);
		application = (BaseApplication) getApplication();
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {

		mHeaderLayout = (HeaderLayout) findViewById(R.id.message_info_header);
		mAvatar = (ImageView) findViewById(R.id.message_info_iv_avatar);
		mHtvName = (HandyTextView) findViewById(R.id.message_info_htv_name);
		mLayoutGender = (LinearLayout) findViewById(R.id.message_info_layout_gender);
		mIvGender = (ImageView) findViewById(R.id.message_info_iv_gender);
		mHtvAge = (HandyTextView) findViewById(R.id.message_info_htv_age);
		mEtvMsgTitle = (EmoticonsTextView) findViewById(R.id.message_friend_htv_interest_title1);
		mEtvMsgDate = (EmoticonsTextView) findViewById(R.id.message_friend_help_date1);
		mEtvMsgTime = (EmoticonsTextView) findViewById(R.id.message_friend_help_time_quantum1);
		mEtvMsgRemark = (HandyTextView) findViewById(R.id.message_friend_remark_content);
		mLayoutChat = (LinearLayout) findViewById(R.id.otherprofile_bottom_layout_chat);
		mLayoutTrade = (LinearLayout) findViewById(R.id.otherprofile_bottom_layout_trade);
		mLayoutReview = (LinearLayout) findViewById(R.id.otherprofile_bottom_layout_review);
		mHtvSign = (HandyTextView)findViewById(R.id.user_item_htv_sign);
		// mLvList = (ListView) findViewById(R.id.feedprofile_lv_list);
		
		mIvVip1 = (ImageView)findViewById(R.id.user_item_iv_icon_vip1);
		mIvVip2 = (ImageView)findViewById(R.id.user_item_iv_icon_vip2);
		mIvVip3 = (ImageView)findViewById(R.id.user_item_iv_icon_vip3);
		mIvStar1 = (ImageView)findViewById(R.id.user_item_iv_icon_star1);
		mIvStar2 = (ImageView)findViewById(R.id.user_item_iv_icon_star2);
		mIvStar3 = (ImageView)findViewById(R.id.user_item_iv_icon_star3);
		
		
	 getLevelByNum();
		 
		 if(vipNum == 1)
			 mIvVip1.setVisibility(View.VISIBLE);
		 else if(vipNum == 2)
		 {
			 mIvVip1.setVisibility(View.VISIBLE);
			 mIvVip2.setVisibility(View.VISIBLE);
		 }
		 else if(vipNum == 3)
		 {
			 mIvVip1.setVisibility(View.VISIBLE);
			 mIvVip2.setVisibility(View.VISIBLE);
			 mIvVip3.setVisibility(View.VISIBLE);
		 }
		 
		 if(starNum == 1)
			 mIvStar1.setVisibility(View.VISIBLE);
		 else if(starNum == 2)
		 {
			 mIvStar1.setVisibility(View.VISIBLE);
			 mIvStar2.setVisibility(View.VISIBLE);
		 }
		 else if(starNum ==3)
		 {
			 mIvStar1.setVisibility(View.VISIBLE);
			 mIvStar2.setVisibility(View.VISIBLE);
			 mIvStar3.setVisibility(View.VISIBLE);
		 }
	}
	
	public void getLevelByNum()
	{
		
		int temp  = (int) (Math.random()*15);	
			
			vipNum = temp/4;
			starNum = temp%3;
	}

	@Override
	protected void initEvents() {

		mLayoutChat.setOnClickListener(this);
		mLayoutTrade.setOnClickListener(this);
		mLayoutReview.setOnClickListener(this);
	}

	private void init() {
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);

		Bundle bundle = null;
		bundle = this.getIntent().getExtras();
		int TAG = bundle.getInt("TAG");

		if (TAG == Config.TAG_HelpMeMessage) {
			mHeaderLayout.setDefaultTitle("帮我消息", null);
			cOrderMsg = new COrderMsg();
			helpMeMsg = (MySHelpMeMsg) bundle.getSerializable("helpMeMessage");

			cOrderMsg.setHelpme_id(helpMeMsg.getHelpme_id());
			cOrderMsg.setHelpID(helpMeMsg.getHelpUserID());
			cOrderMsg.setBeHelpedID(helpMeMsg.getBeHelpedUserID());
			cOrderMsg.setEndTime(helpMeMsg.getEndTime());
			cOrderMsg.setStartTime(helpMeMsg.getStartTime());
			cOrderMsg.setReqItemID(helpMeMsg.getReqItemID());
			cOrderMsg.setReqDetail(helpMeMsg.getReqDetail());
			cOrderMsg.setOrderType(OrderMsgType.REQUEST);

			mHtvName.setText(helpMeMsg.getHelpName());
			mEtvMsgTitle.setText(helpMeMsg.getReqItem());
			mEtvMsgDate.setText(helpMeMsg.getMsgTime());
			mEtvMsgTime.setText(helpMeMsg.getStartTime() + "~"
					+ helpMeMsg.getEndTime());
			mEtvMsgRemark.setText(helpMeMsg.getReqDetail());
			// 根据ID从数据库中获取hibangUser
			if (currentUser == null) {
				CUserInfoRequest msg = new CUserInfoRequest();
				msg.setUserId(helpMeMsg.getHelpUserID());
				application.client.sendMessage(msg);
				return;
			}

			mHtvAge.setText(currentUser.getUserAge().toString());
			mHtvSign.setText(currentUser.getPs());

			if (currentUser.getGender() == GENDER.MALE) {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
				mIvGender.setImageResource(R.drawable.ic_user_male);
			} else {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
				mIvGender.setImageResource(R.drawable.ic_user_famale);
			}
			setPhoto(currentUser.getUserID());

		} else if (TAG == Config.TAG_MeHelpMessage) {
			mHeaderLayout.setDefaultTitle("我帮消息", null);
			cOrderMsg = new COrderMsg();
			meHelpMsg = (MySMeHelpMsg) bundle.getSerializable("meHelpMessage");

			cOrderMsg.setHelpme_id(meHelpMsg.getHelpme_id());
			cOrderMsg.setHelpID(meHelpMsg.getHelpUserID());
			cOrderMsg.setBeHelpedID(meHelpMsg.getBeHelpedUserID());
			cOrderMsg.setEndTime(meHelpMsg.getEndTime());
			cOrderMsg.setStartTime(meHelpMsg.getStartTime());
			cOrderMsg.setReqItemID(meHelpMsg.getReqItemID());
			cOrderMsg.setReqDetail(meHelpMsg.getReqDetail());
			cOrderMsg.setOrderType(OrderMsgType.REQUEST);

			mHtvName.setText(meHelpMsg.getHelpName());
			mEtvMsgTitle.setText(meHelpMsg.getReqItem());
			mEtvMsgDate.setText(meHelpMsg.getMsgTime());
			mEtvMsgTime.setText(meHelpMsg.getStartTime() + "~"
					+ meHelpMsg.getEndTime());
			mEtvMsgRemark.setText(meHelpMsg.getReqDetail());

			// 根据ID从数据库中获取hibangUser
			if (currentUser == null) {
				CUserInfoRequest msg = new CUserInfoRequest();
				msg.setUserId(meHelpMsg.getBeHelpedUserID());
				application.client.sendMessage(msg);
				return;
			}
			mHtvAge.setText(currentUser.getUserAge().toString());
			mHtvSign.setText(currentUser.getPs());

			if (currentUser.getGender() == GENDER.MALE) {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
				mIvGender.setImageResource(R.drawable.ic_user_male);
			} else {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
				mIvGender.setImageResource(R.drawable.ic_user_famale);
			}
			
			setPhoto(currentUser.getUserID());

		} else if (TAG == Config.TAG_SOrderMsg) {
			mHeaderLayout.setDefaultTitle("订单消息", null);
			SOrderMsg sOrderMsg = (SOrderMsg) bundle
					.getSerializable("SOrderMsg");
			
			mEtvMsgTitle.setText("当前订单号："+sOrderMsg.getReqItemID());
			mEtvMsgDate.setText(sOrderMsg.getStartTime());
			mEtvMsgTime.setText(sOrderMsg.getStartTime() + "~"
					+ sOrderMsg.getEndTime());
			mEtvMsgRemark.setText(sOrderMsg.getReqDetail());
			// 根据ID从数据库中获取hibangUser
			if (currentUser == null) {
				CUserInfoRequest msg = new CUserInfoRequest();
				if (sOrderMsg.getHelpID() == application.getUser().getUserID()) {
					msg.setUserId(sOrderMsg.getBeHelpedID());
				} else {
					msg.setUserId(sOrderMsg.getHelpID());
				}

				application.client.sendMessage(msg);
				return;
			}
			mHtvName.setText(currentUser.getUsername());
			mHtvAge.setText(currentUser.getUserAge().toString());

			if (currentUser.getGender() == GENDER.MALE) {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
				mIvGender.setImageResource(R.drawable.ic_user_male);
			} else {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
				mIvGender.setImageResource(R.drawable.ic_user_famale);
			}
			setPhoto(currentUser.getUserID());
		} else if(TAG == Config.TAG_RecomMessage) {
			mHeaderLayout.setDefaultTitle("订单消息", null);
//			MyUserRequire myUserRequire = (MyUserRequire) bundle.getSerializable("recomMessage");
			cOrderMsg = new COrderMsg();
			
			;
			;
			;
			;
			;
			;
			;

			cOrderMsg.setHelpme_id(bundle.getInt("Helpme_id"));
			cOrderMsg.setHelpID(application.getUser().getUserID());
			cOrderMsg.setBeHelpedID(bundle.getInt("userId"));
			cOrderMsg.setEndTime(bundle.getString("EndTime"));
			cOrderMsg.setStartTime(bundle.getString("StartTime"));
			cOrderMsg.setReqItemID(bundle.getInt("ReqItemID"));
			cOrderMsg.setReqDetail(bundle.getString("ReqDetail"));
			cOrderMsg.setOrderType(OrderMsgType.REQUEST);

			
			mEtvMsgTitle.setText(bundle.getString("ReqItemName"));
			mEtvMsgDate.setText(bundle.getString("StartTime"));
			mEtvMsgTime.setText(bundle.getString("StartTime") + "~"
					+ bundle.getString("EndTime"));
			mEtvMsgRemark.setText(bundle.getString("ReqDetail"));
			// 根据ID从数据库中获取hibangUser
			if (currentUser == null) {
				CUserInfoRequest msg = new CUserInfoRequest();
				msg.setUserId(bundle.getInt("userId"));
				application.client.sendMessage(msg);
				return;
			}
			mHtvName.setText(currentUser.getUsername());
			mHtvAge.setText(currentUser.getUserAge().toString());
			mHtvSign.setText(currentUser.getPs());

			if (currentUser.getGender() == GENDER.MALE) {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
				mIvGender.setImageResource(R.drawable.ic_user_male);
			} else {
				mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
				mIvGender.setImageResource(R.drawable.ic_user_famale);
			}
			setPhoto(currentUser.getUserID());
		}

	}

	private void setPhoto(int id) {
		String path = DBManage.getPhotoPathById(id);

		if (path == null  || path.equals("") ) {
			mAvatar.setImageResource(R.drawable.nearby_people_8);
			CPhotoRequestMsg msg = new CPhotoRequestMsg();
			msg.setUserID(id);
			mApplication.client.sendMessage(msg);
		} else {
			try {
				mAvatar.setImageBitmap(BitmapFactory
						.decodeStream(new FileInputStream(path)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.otherprofile_bottom_layout_chat:
			Bundle bundle = new Bundle();
			Intent intent = new Intent(this, ChatActivity.class);
			bundle.putInt("friendId", currentUser.getUserID());
			bundle.putString("friendName", currentUser.getUsername());
//		     bundle.putSerializable("friendUser", friendUser);
			 intent.putExtras(bundle);
			 startActivity(intent);
			break;
		case R.id.otherprofile_bottom_layout_trade:
			if (cOrderMsg != null) {
				new AlertDialog.Builder(InformationActivity.this)
						.setTitle("温馨提示")
						.setMessage("确定要交易吗？")
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										application.client
												.sendMessage(cOrderMsg);
									}
								})
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).create().show();

			}

			break;
		case R.id.otherprofile_bottom_layout_review:
			startActivity(new Intent(InformationActivity.this,
					InfoReviewActivity.class));

			break;

		default:
			break;
		}
	}

	private final int HELP_ME_MSG_RECEIVED = 1;
	private final int ME_HELP_MSG_RECEIVED = 2;
	private final int USERINFOREQMSGMSG_RECEIVED = 3;

	@Override
	public void onUserInfoReqMsg(SUserInfoRequest request) {
		Message msg = new Message();
		msg.obj = request;
		msg.what = USERINFOREQMSGMSG_RECEIVED;
		handler.sendMessage(msg);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case USERINFOREQMSGMSG_RECEIVED:
				HiBangUser user = ((SUserInfoRequest) msg.obj).getUser();
				currentUser = user;
				init();
				break;
			case ME_HELP_MSG_RECEIVED:
				SOrderMsg orderMsg = ((SOrderMsg) msg.obj);
				String content = "";
					if(orderMsg.getOrderType() == OrderMsgType.REQUEST) {
						content = "嗨，有人帮助你啦，快来看吧！";
					} else {
						if(orderMsg.isbOrdered()) {
							content = "嗨，你与 "+currentUser.getUsername()+ " 已经正在交易中了";
						}
					}
				new AlertDialog.Builder(InformationActivity.this)
						.setTitle("温馨提示")
						.setMessage(content)
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										
									}
								})
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).create().show();
				break;
			case HELP_ME_MSG_RECEIVED:

				break;
			default:
				break;
			}

		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.KEYCODE_BACK == keyCode) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		SMsgManage.getManager().setsUserInfoRequestListener(this);
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}

	@Override
	public void onSOrderMsgReceived(SOrderMsg sOrderMsg) {
		Message msg = new Message();
		msg.obj = sOrderMsg;
		if (sOrderMsg.getBeHelpedID() != application.getUser().getUserID()) {
			msg.what = ME_HELP_MSG_RECEIVED;
		} else {
			msg.what = HELP_ME_MSG_RECEIVED;
		}
		handler.sendMessage(msg);

	}
	
	

}
