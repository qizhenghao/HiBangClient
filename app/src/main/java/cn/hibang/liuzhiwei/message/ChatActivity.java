package cn.hibang.liuzhiwei.message;

import java.util.List;

import com.example.newhaibang.R;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.telephony.gsm.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.domain.MyChatMsg;
import cn.hibang.bruce.interf.MyChattingListener;
import cn.hibang.bruce.interf.SChatMessageListener;
import cn.hibang.huxing.clientmessage.CChatMessage;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.servermessage.SChatMessage;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.adapter.ChatAdapter;
import cn.hibang.liuzhiwei.testentity.MyMessage;
import cn.hibang.liuzhiwei.testentity.MyMessage.CONTENT_TYPE;
import cn.hibang.liuzhiwei.testentity.MyMessage.MESSAGE_TYPE;
import cn.hibang.liuzhiwei.util.DateUtils;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.ChatListView;
import cn.hibang.liuzhiwei.view.EmoteInputView;
import cn.hibang.liuzhiwei.view.EmoticonsEditText;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.ScrollLayout;
import cn.hibang.liuzhiwei.view.HeaderLayout.HeaderStyle;
import cn.hibang.liuzhiwei.view.HeaderLayout.onMiddleImageButtonClickListener;

public class ChatActivity extends BaseMessageActivity implements
		MyChattingListener{

	private BaseApplication application = null;
	private HiBangUser user = null;
//	private HiBangUser friendUser = null;
	private Integer friendId = null;
	private String friendName = null;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (BaseApplication) getApplication();
		SMsgManage.getManager().setCurrContext(ChatActivity.this);
		SMsgManage.getManager().setMyChattingListener(this);		
		SMsgManage.contextMap.put(Config.TAG_ChatActivity, this);
		user = application.getUser();
		init();
	}

	// 点击后退按钮效果
	@Override
	public void onBackPressed() {
		// 隐藏左下角功能条
		if (mLayoutMessagePlusBar.isShown()) {
			hidePlusBar();
		}
		// 隐藏表情图片栏
		else if (mInputView.isShown()) {
			mIbTextDitorKeyBoard.setVisibility(View.GONE);
			mIbTextDitorEmote.setVisibility(View.VISIBLE);
			mInputView.setVisibility(View.GONE);
		}
		// 隐藏手机键盘输入法
		else if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
			mIbTextDitorKeyBoard.setVisibility(View.VISIBLE);
			mIbTextDitorEmote.setVisibility(View.GONE);
			hideKeyBoard();
		}
		//
		// else if (mLayoutScroll.getCurScreen() == 1) {
		// mLayoutScroll.snapToScreen(0);
		// }
		else {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		// PhotoUtils.deleteImageFile();
		SMsgManage.getManager().setChattingUserId(-1);
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		SMsgManage.getManager().setChattingUserId(friendId);
		super.onResume();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.chat_header);
		mHeaderLayout.init(HeaderStyle.TITLE_CHAT);
		mClvList = (ChatListView) findViewById(R.id.chat_clv_list);
		// mLayoutScroll = (ScrollLayout)
		// findViewById(R.id.chat_slayout_scroll);
		// mLayoutRounds = (LinearLayout) findViewById(R.id.chat_layout_rounds);
		mInputView = (EmoteInputView) findViewById(R.id.chat_eiv_inputview);

		mIbTextDitorPlus = (ImageButton) findViewById(R.id.chat_textditor_ib_plus);
		mIbTextDitorKeyBoard = (ImageButton) findViewById(R.id.chat_textditor_ib_keyboard);
		mIbTextDitorEmote = (ImageButton) findViewById(R.id.chat_textditor_ib_emote);
		mIvTextDitorAudio = (ImageView) findViewById(R.id.chat_textditor_iv_audio);
		mBtnTextDitorSend = (Button) findViewById(R.id.chat_textditor_btn_send);
		mEetTextDitorEditer = (EmoticonsEditText) findViewById(R.id.chat_textditor_eet_editer);

		// mIbAudioDitorPlus = (ImageButton)
		// findViewById(R.id.chat_audioditor_ib_plus);
		// mIbAudioDitorKeyBoard = (ImageButton)
		// findViewById(R.id.chat_audioditor_ib_keyboard);
		// mIvAudioDitorAudioBtn = (ImageView)
		// findViewById(R.id.chat_audioditor_iv_audiobtn);

		mLayoutFullScreenMask = (LinearLayout) findViewById(R.id.fullscreen_mask);
		mLayoutMessagePlusBar = (LinearLayout) findViewById(R.id.message_plus_layout_bar);
		mLayoutMessagePlusPicture = (LinearLayout) findViewById(R.id.message_plus_layout_picture);
		mLayoutMessagePlusCamera = (LinearLayout) findViewById(R.id.message_plus_layout_camera);
		mLayoutMessagePlusLocation = (LinearLayout) findViewById(R.id.message_plus_layout_location);
		mLayoutMessagePlusGift = (LinearLayout) findViewById(R.id.message_plus_layout_gift);

	}

	@Override
	protected void initEvents() {
		// mLayoutScroll.setOnScrollToScreen(this);
		mIbTextDitorPlus.setOnClickListener(this);
		mIbTextDitorEmote.setOnClickListener(this);
		mIbTextDitorKeyBoard.setOnClickListener(this);
		mBtnTextDitorSend.setOnClickListener(this);
		mIvTextDitorAudio.setOnClickListener(this);
		mEetTextDitorEditer.addTextChangedListener(this);
		mEetTextDitorEditer.setOnTouchListener(this);
		// mIbAudioDitorPlus.setOnClickListener(this);
		// mIbAudioDitorKeyBoard.setOnClickListener(this);

		mLayoutFullScreenMask.setOnTouchListener(this);
		mLayoutMessagePlusPicture.setOnClickListener(this);
		mLayoutMessagePlusCamera.setOnClickListener(this);
		mLayoutMessagePlusLocation.setOnClickListener(this);
		mLayoutMessagePlusGift.setOnClickListener(this);

	}

	private void init() {
	
		Bundle bundle = null;
		bundle = this.getIntent().getExtras();
//		friendUser = (HiBangUser)bundle.getSerializable("friendUser");
//		friendId = friendUser.getUserID();
		friendId = bundle.getInt("friendId");
		SMsgManage.getManager().setChattingUserId(friendId);
//		friendName = friendUser.getUsername();
		friendName = bundle.getString("friendName");


		// 初始化标题栏（显示对话人的名字及两个功能按钮）
		mHeaderLayout.setTitleChat(R.drawable.ic_chat_dis_1,
				R.drawable.bg_chat_dis_active, "与" +friendName+ "对话", "0.1km" + " "
						+ "30分钟前", R.drawable.ic_topbar_profile,
				new OnMiddleImageButtonClickListener(),
				R.drawable.ic_topbar_more,
				new OnRightImageButtonClickListener());
		mInputView.setEditText(mEetTextDitorEditer);
		// initRounds();
		initPopupWindow();
		// initSynchronousDialog();

		// 对话聊天Listview的适配器
		mAdapter = new ChatAdapter(mApplication, ChatActivity.this, mMessages);
		mClvList.setAdapter(mAdapter);
		
		List<MyChatMsg> list = DBManage.getChatMsgByFriendId(friendId, ++count);
		MyMessage currentMsg = null;
		for(int i = list.size()-1;i >=0 ;i--)
		{
			if(list.get(i).getSenderId() == user.getUserID())
			{
				currentMsg = new MyMessage("nearby_people_other", list.get(i).getChatTime(), "0.12km", list.get(i).getMsgContent(),
						 CONTENT_TYPE.TEXT, MESSAGE_TYPE.SEND,user.getUserID());
			}
			else
			{
				currentMsg = new MyMessage("nearby_people_other", list.get(i).getChatTime(), "0.12km", list.get(i).getMsgContent(),
						 CONTENT_TYPE.TEXT, MESSAGE_TYPE.RECEIVER,list.get(i).getSenderId());
			}
			
			mMessages.add(currentMsg);
			mAdapter.notifyDataSetChanged();
			mClvList.setSelection(mMessages.size());
									
		}
		
		
	}
	
	//点击右上角个人资料按钮监听
	protected class OnMiddleImageButtonClickListener implements
			onMiddleImageButtonClickListener {

		@Override
		public void onClick() {
               Intent intent = new Intent(ChatActivity.this, ChatFriendInfoActivity.class);
			   Bundle bundle = new Bundle();
//			   bundle.putSerializable("friendUser", friendUser);
			   bundle.putInt("friendId", friendId);
			   intent.putExtras(bundle);
			   startActivity(intent);
			
		}
	}

	// public void doAction(int whichScreen) {
	// switch (whichScreen) {
	// case 0:
	// ((ImageView) mLayoutRounds.getChildAt(0))
	// .setImageBitmap(mRoundsSelected);
	// ((ImageView) mLayoutRounds.getChildAt(1))
	// .setImageBitmap(mRoundsNormal);
	// break;
	//
	// case 1:
	// ((ImageView) mLayoutRounds.getChildAt(1))
	// .setImageBitmap(mRoundsSelected);
	// ((ImageView) mLayoutRounds.getChildAt(0))
	// .setImageBitmap(mRoundsNormal);
	// mIbTextDitorKeyBoard.setVisibility(View.GONE);
	// mIbTextDitorEmote.setVisibility(View.VISIBLE);
	// if (mInputView.isShown()) {
	// mInputView.setVisibility(View.GONE);
	// }
	// hideKeyBoard();
	// break;
	// }
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击屏幕左下角加号显示功能选项条
		case R.id.chat_textditor_ib_plus:
			if (!mLayoutMessagePlusBar.isShown()) {
				showPlusBar();
			}
			break;
		// 点击左下角表情按钮
		case R.id.chat_textditor_ib_emote:
			mIbTextDitorKeyBoard.setVisibility(View.VISIBLE);
			mIbTextDitorEmote.setVisibility(View.GONE);
			mEetTextDitorEditer.requestFocus();
			if (mInputView.isShown()) {
				hideKeyBoard();
			} else {
				hideKeyBoard();
				mInputView.setVisibility(View.VISIBLE);
			}
			break;
		// 点击左下角键盘按钮
		case R.id.chat_textditor_ib_keyboard:
			mIbTextDitorKeyBoard.setVisibility(View.GONE);
			mIbTextDitorEmote.setVisibility(View.VISIBLE);
			showKeyBoard();
			break;
		// 点击右下角发送按钮发送文本
		case R.id.chat_textditor_btn_send:
			String content = mEetTextDitorEditer.getText().toString().trim();
			if (!TextUtils.isEmpty(content)) {
				mEetTextDitorEditer.setText(null);
				CChatMessage msg = new CChatMessage();
				msg.setUserID(user.getUserID());
				msg.setFriendID(friendId);
				msg.setChatTime(DateUtils.formatDate(ChatActivity.this,  System
				 .currentTimeMillis()));
				msg.setChatContent(content);
				application.client.sendMessage(msg);
				DBManage.addCChatMsg(msg);

				 mMessages.add(new MyMessage("nearby_people_other", DateUtils.formatDate(ChatActivity.this,  System
						 .currentTimeMillis()), "0.12km", content,
				 CONTENT_TYPE.TEXT, MESSAGE_TYPE.SEND,user.getUserID()));
				mAdapter.notifyDataSetChanged();
				mClvList.setSelection(mMessages.size());
			}
			break;
		// 点击右下角语音按钮，将底部功能栏滚动到另一边
		case R.id.chat_textditor_iv_audio:
			// mLayoutScroll.snapToScreen(1);
			break;
		// 点击语音栏的加号按钮，显示图片照相功能条
		// case R.id.chat_audioditor_ib_plus:
		// if (!mLayoutMessagePlusBar.isShown()) {
		// showPlusBar();
		// }
		// break;
		// 点击语音栏右下角键盘按钮，将底部功能栏滑回到另一边
		// case R.id.chat_audioditor_ib_keyboard:
		// mLayoutScroll.snapToScreen(0);
		// break;
		// 点击图片按钮
		case R.id.message_plus_layout_picture:
			// PhotoUtils.selectPhoto(ChatActivity.this);
			hidePlusBar();
			break;
		// 点击照相按钮
		case R.id.message_plus_layout_camera:
			// mCameraImagePath = PhotoUtils.takePicture(ChatActivity.this);
			hidePlusBar();
			break;
		// 点击位置按钮
		case R.id.message_plus_layout_location:
			// mMessages.add(new Message("nearby_people_other", System
			// .currentTimeMillis(), "0.12km", null, CONTENT_TYPE.MAP,
			// MESSAGE_TYPE.SEND));
			// mAdapter.notifyDataSetChanged();
			// mClvList.setSelection(mMessages.size());
			hidePlusBar();
			break;
		// 点击位置按钮
		case R.id.message_plus_layout_gift:
			hidePlusBar();
			break;
		}
	}

	// 监听点击屏幕事件
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 点击信息编辑框事件
		if (v.getId() == R.id.chat_textditor_eet_editer) {
			mIbTextDitorKeyBoard.setVisibility(View.GONE);
			mIbTextDitorEmote.setVisibility(View.VISIBLE);
			showKeyBoard();
		}
		// 点击屏幕其他地方隐藏功能条
		if (v.getId() == R.id.fullscreen_mask) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				hidePlusBar();
			}
		}
		return false;
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	// 若编辑栏不为空，则左下角显示发送按钮，否则显示语音按钮
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (TextUtils.isEmpty(s)) {
			mIvTextDitorAudio.setVisibility(View.VISIBLE);
			mBtnTextDitorSend.setVisibility(View.GONE);
		} else {
			mIvTextDitorAudio.setVisibility(View.GONE);
			mBtnTextDitorSend.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onVoiceModeClick() {
		// String[] modes =
		// getResources().getStringArray(R.array.chat_audio_type);
		// mDialog = new SimpleListDialog(this);
		// mDialog.setTitle("语音收听方式");
		// mDialog.setTitleLineVisibility(View.GONE);
		// mDialog.setAdapter(new CheckListDialogAdapter(mCheckId, this,
		// modes));
		// mDialog.setOnSimpleListItemClickListener(new
		// OnVoiceModeDialogItemClickListener());
		// mDialog.show();
	}

	@Override
	public void onCreateClick() {

	}

	@Override
	public void onSynchronousClick() {
		// mSynchronousDialog.show();
	}

	// @SuppressWarnings("deprecation")
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// switch (requestCode) {
	// case PhotoUtils.INTENT_REQUEST_CODE_ALBUM:
	// if (data == null) {
	// return;
	// }
	// if (resultCode == RESULT_OK) {
	// if (data.getData() == null) {
	// return;
	// }
	// if (!FileUtils.isSdcardExist()) {
	// showCustomToast("SD卡不可用,请检查");
	// return;
	// }
	// Uri uri = data.getData();
	// String[] proj = { MediaStore.Images.Media.DATA };
	// Cursor cursor = managedQuery(uri, proj, null, null, null);
	// if (cursor != null) {
	// int column_index = cursor
	// .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	// if (cursor.getCount() > 0 && cursor.moveToFirst()) {
	// String path = cursor.getString(column_index);
	// Bitmap bitmap = PhotoUtils.getBitmapFromFile(path);
	// if (PhotoUtils.bitmapIsLarge(bitmap)) {
	// PhotoUtils.cropPhoto(this, this, path);
	// } else {
	// if (path != null) {
	// mMessages.add(new Message(
	// "nearby_people_other", System
	// .currentTimeMillis(), "0.12km",
	// path, CONTENT_TYPE.IMAGE,
	// MESSAGE_TYPE.SEND));
	// mAdapter.notifyDataSetChanged();
	// mClvList.setSelection(mMessages.size());
	// }
	// }
	// }
	// }
	// }
	// break;
	//
	// case PhotoUtils.INTENT_REQUEST_CODE_CAMERA:
	// if (resultCode == RESULT_OK) {
	// if (mCameraImagePath != null) {
	// mCameraImagePath = PhotoUtils
	// .savePhotoToSDCard(PhotoUtils.CompressionPhoto(
	// mScreenWidth, mCameraImagePath, 2));
	// PhotoUtils.fliterPhoto(this, this, mCameraImagePath);
	// }
	// }
	// mCameraImagePath = null;
	// break;
	//
	// case PhotoUtils.INTENT_REQUEST_CODE_CROP:
	// if (resultCode == RESULT_OK) {
	// String path = data.getStringExtra("path");
	// if (path != null) {
	// mMessages.add(new Message("nearby_people_other", System
	// .currentTimeMillis(), "0.12km", path,
	// CONTENT_TYPE.IMAGE, MESSAGE_TYPE.SEND));
	// mAdapter.notifyDataSetChanged();
	// mClvList.setSelection(mMessages.size());
	// }
	// }
	// break;
	//
	// case PhotoUtils.INTENT_REQUEST_CODE_FLITER:
	// if (resultCode == RESULT_OK) {
	// String path = data.getStringExtra("path");
	// if (path != null) {
	// mMessages.add(new Message("nearby_people_other", System
	// .currentTimeMillis(), "0.12km", path,
	// CONTENT_TYPE.IMAGE, MESSAGE_TYPE.SEND));
	// mAdapter.notifyDataSetChanged();
	// mClvList.setSelection(mMessages.size());
	// }
	// }
	// break;
	// }
	// }
	// 有信息发送时更新适配器
	public void refreshAdapter() {
		mAdapter.notifyDataSetChanged();
	}

	private final int MSG_RECEIVED = 1;


	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_RECEIVED:
				SChatMessage receiveMsg = (SChatMessage)msg.obj;

				 mMessages.add(new MyMessage("nearby_people_other", receiveMsg.getChatTime(), "0.12km", receiveMsg.getChatContent(),
				 CONTENT_TYPE.TEXT, MESSAGE_TYPE.RECEIVER,receiveMsg.getSenderID()));
				mAdapter.notifyDataSetChanged();
				mClvList.setSelection(mMessages.size());

				break;
			}
		}
	};


	@Override
	public void onMsgReceived(SChatMessage sChatMessage) {
		Message msg = new Message();
		msg.what = this.MSG_RECEIVED;
		msg.obj = sChatMessage;
		handler.sendMessage(msg);
	}}
