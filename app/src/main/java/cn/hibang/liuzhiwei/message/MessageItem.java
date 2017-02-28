package cn.hibang.liuzhiwei.message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.newhaibang.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.testentity.MyMessage;
import cn.hibang.liuzhiwei.testentity.MyMessage.MESSAGE_TYPE;
import cn.hibang.liuzhiwei.util.DateUtils;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.HandyTextView;

public abstract class MessageItem {

	protected Context mContext;
	protected View mRootView;

	/**
	 * TimeStampContainer
	 */
	private RelativeLayout mLayoutTimeStampContainer;
	private HandyTextView mHtvTimeStampTime;
	private HandyTextView mHtvTimeStampDistance;

	/**
	 * LeftContainer
	 */
	private RelativeLayout mLayoutLeftContainer;
	private LinearLayout mLayoutStatus;
	private HandyTextView mHtvStatus;

	/**
	 * MessageContainer
	 */
	protected LinearLayout mLayoutMessageContainer;

	/**
	 * RightContainer
	 */
	private LinearLayout mLayoutRightContainer;
	private ImageView mIvPhotoView;

	protected LayoutInflater mInflater;
	protected MyMessage mMsg;

	protected int mBackground;
	private BaseApplication mApplication;
	
	//点击头像查看个人资料按钮监听
	protected onClickAvatarListener mOnClickAvatarListener;

	public MessageItem(MyMessage msg, Context context, BaseApplication mApplication) {
		mMsg = msg;
		mContext = context;
		this.mApplication = mApplication;
		mInflater = LayoutInflater.from(context);
	}

	public static MessageItem getInstance(MyMessage msg, Context context, BaseApplication mApplication) {
		MessageItem messageItem = null;
		switch (msg.getContentType()) {
		case TEXT:
			messageItem = new TextMessageItem(msg, context, mApplication);
			break;

		case IMAGE:
			//messageItem = new ImageMessageItem(msg, context);
			break;

		case MAP:
			//messageItem = new MapMessageItem(msg, context);
			break;

		case VOICE:
			//messageItem = new VoiceMessageItem(msg, context);
			break;

		}
		messageItem.init(msg.getMessageType());
		return messageItem;
	}

	private void init(MESSAGE_TYPE messageType) {
		switch (messageType) {
		case RECEIVER:
			mRootView = mInflater.inflate(R.layout.message_group_receive_template,
					null);
			mBackground = R.drawable.bg_message_box_receive;
			break;

		case SEND:
			mRootView = mInflater.inflate(R.layout.message_group_send_template,
					null);
			mBackground = R.drawable.bg_message_box_send;
			break;
		}
		if (mRootView != null) {
			initViews(mRootView);
		}
	}

	protected void initViews(View view) {
		mLayoutTimeStampContainer = (RelativeLayout) view
				.findViewById(R.id.message_layout_timecontainer);
		mHtvTimeStampTime = (HandyTextView) view
				.findViewById(R.id.message_timestamp_htv_time);
		mHtvTimeStampDistance = (HandyTextView) view
				.findViewById(R.id.message_timestamp_htv_distance);

		mLayoutLeftContainer = (RelativeLayout) view
				.findViewById(R.id.message_layout_leftcontainer);
		mLayoutStatus = (LinearLayout) view
				.findViewById(R.id.message_layout_status);
		mHtvStatus = (HandyTextView) view.findViewById(R.id.message_htv_status);

		mLayoutMessageContainer = (LinearLayout) view
				.findViewById(R.id.message_layout_messagecontainer);
		mLayoutMessageContainer.setBackgroundResource(mBackground);

		mLayoutRightContainer = (LinearLayout) view
				.findViewById(R.id.message_layout_rightcontainer);
		mIvPhotoView = (ImageView) view.findViewById(R.id.message_iv_userphoto);
		onInitViews();
		
		mIvPhotoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				mOnClickAvatarListener.onClick();
				
			}
		});
	}

	public void fillContent() {
		fillTimeStamp();
		fillStatus();
		fillMessage();
		fillPhotoView();
	}

	protected void fillMessage() {
		onFillMessage();
	}

	protected void fillTimeStamp() {
		mLayoutTimeStampContainer.setVisibility(View.VISIBLE);
		if (mMsg.getTime() != null) {
			mHtvTimeStampTime.setText(mMsg.getTime());
		}
		if (mMsg.getDistance() != null) {
			mHtvTimeStampDistance.setText(mMsg.getDistance());
		} else {
			mHtvTimeStampDistance.setText("未知");
		}
	}

	protected void fillStatus() {
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
		mLayoutStatus
				.setBackgroundResource(R.drawable.bg_message_status_sended);
		mHtvStatus.setText("送达");
	}

	protected void fillPhotoView() {
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mIvPhotoView.setImageBitmap(PhotoUtils.getPortraitById(mMsg.getAvatarId(), mApplication));
//		mIvPhotoView.setImageResource(R.drawable.my_test_chating_image);
	
		//从数据库中获取头像
//		String path = DBManage.getPhotoPathById(mMsg.getAvatarId());
//
//		if (path == null  || path.equals("") ) {
//			mIvPhotoView.setImageResource(R.drawable.my_test_chating_image);
//			CPhotoRequestMsg msg = new CPhotoRequestMsg();
//			msg.setUserID(mMsg.getAvatarId());
//			mApplication.client.sendMessage(msg);
//		} else {
//			try {
//				mIvPhotoView.setImageBitmap(BitmapFactory
//						.decodeStream(new FileInputStream(path)));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}

		
	}

	protected void refreshAdapter() {
		((ChatActivity) mContext).refreshAdapter();
	}

	public View getRootView() {
		return mRootView;
	}

	protected abstract void onInitViews();

	protected abstract void onFillMessage();
	
	//聊天界面点击头像显示个人资料
	public void setOnClickAvatarListener(onClickAvatarListener l)
	{
		mOnClickAvatarListener = l;
	}
	
	public interface onClickAvatarListener
	{
		void onClick();
	}
}