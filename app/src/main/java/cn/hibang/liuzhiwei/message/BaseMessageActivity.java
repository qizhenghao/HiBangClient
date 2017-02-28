package cn.hibang.liuzhiwei.message;


import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.hibang.huxing.msgutility.HiBangUser;
//import com.immomo.momo.android.BaseDialog;
//import com.immomo.momo.android.activity.OtherProfileActivity;
//import com.immomo.momo.android.dialog.SimpleListDialog;
//import com.immomo.momo.android.dialog.SimpleListDialog.onSimpleListItemClickListener;
//import com.immomo.momo.android.entity.NearByPeople;
//import com.immomo.momo.android.entity.NearByPeopleProfile;
//import com.immomo.momo.android.util.PhotoUtils;
import cn.hibang.liuzhiwei.adapter.ChatAdapter;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.maintabs.RecommendActivity;
import cn.hibang.liuzhiwei.popupwindow.ChatPopupWindow;
import cn.hibang.liuzhiwei.popupwindow.ChatPopupWindow.onChatPopupItemClickListener;
import cn.hibang.liuzhiwei.testentity.MyMessage;
import cn.hibang.liuzhiwei.testentity.TestFriend;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.ChatListView;
import cn.hibang.liuzhiwei.view.EmoteInputView;
import cn.hibang.liuzhiwei.view.EmoticonsEditText;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.ScrollLayout;
import cn.hibang.liuzhiwei.view.HeaderLayout.onMiddleImageButtonClickListener;
import cn.hibang.liuzhiwei.view.HeaderLayout.onRightImageButtonClickListener;
import cn.hibang.liuzhiwei.view.ScrollLayout.OnScrollToScreenListener;

public abstract class BaseMessageActivity extends BaseActivity implements OnClickListener, OnTouchListener,
TextWatcher, onChatPopupItemClickListener {
	
	
	protected HeaderLayout mHeaderLayout;
	protected ChatListView mClvList;
	protected ScrollLayout mLayoutScroll;
	protected LinearLayout mLayoutRounds;
	protected EmoteInputView mInputView;

	protected ImageButton mIbTextDitorPlus;
	protected ImageButton mIbTextDitorKeyBoard;
	protected ImageButton mIbTextDitorEmote;
	protected EmoticonsEditText mEetTextDitorEditer;
	protected Button mBtnTextDitorSend;
	protected ImageView mIvTextDitorAudio;

	protected ImageButton mIbAudioDitorPlus;
	protected ImageButton mIbAudioDitorKeyBoard;
	protected ImageView mIvAudioDitorAudioBtn;

	protected LinearLayout mLayoutFullScreenMask;
	protected LinearLayout mLayoutMessagePlusBar;
	protected LinearLayout mLayoutMessagePlusPicture;
	protected LinearLayout mLayoutMessagePlusCamera;
	protected LinearLayout mLayoutMessagePlusLocation;
	protected LinearLayout mLayoutMessagePlusGift;

	protected List<MyMessage> mMessages = new ArrayList<MyMessage>();
	protected ChatAdapter mAdapter;



	protected Bitmap mRoundsSelected;
	protected Bitmap mRoundsNormal;

	private ChatPopupWindow mChatPopupWindow;
	private int mWidth;
	private int mHeaderHeight;
	
//	protected BaseDialog mSynchronousDialog;

	protected String mCameraImagePath;


//	protected SimpleListDialog mDialog;
	protected int mCheckId = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initViews();
		initEvents();
	}


   //点击右上角按钮弹出列表项PopupWindow
	protected class OnRightImageButtonClickListener implements
			onRightImageButtonClickListener {

		@Override
		public void onClick() {
			mChatPopupWindow.showAtLocation(mHeaderLayout, Gravity.RIGHT
					| Gravity.TOP, -10, mHeaderHeight + 10);
		}
	}
    //显示手机键盘输入法
	protected void showKeyBoard() {
		if (mInputView.isShown()) {
			mInputView.setVisibility(View.GONE);
		}
		mEetTextDitorEditer.requestFocus();
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.showSoftInput(mEetTextDitorEditer, 0);
	}
  //隐藏手机键盘输入法
	protected void hideKeyBoard() {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(BaseMessageActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}

	//点击左下角加号显示图片照相位置赠送功能条
	protected void showPlusBar() {
		mLayoutFullScreenMask.setEnabled(true);
		mLayoutMessagePlusBar.setEnabled(true);
		mLayoutMessagePlusPicture.setEnabled(true);
		mLayoutMessagePlusCamera.setEnabled(true);
		mLayoutMessagePlusLocation.setEnabled(true);
		mLayoutMessagePlusGift.setEnabled(true);
		Animation animation = AnimationUtils.loadAnimation(
				BaseMessageActivity.this, R.anim.controller_enter);
		mLayoutMessagePlusBar.setAnimation(animation);
		mLayoutMessagePlusBar.setVisibility(View.VISIBLE);
		mLayoutFullScreenMask.setVisibility(View.VISIBLE);
	}
  //取消图片照相位置赠送功能条
	protected void hidePlusBar() {
		mLayoutFullScreenMask.setEnabled(false);
		mLayoutMessagePlusBar.setEnabled(false);
		mLayoutMessagePlusPicture.setEnabled(false);
		mLayoutMessagePlusCamera.setEnabled(false);
		mLayoutMessagePlusLocation.setEnabled(false);
		mLayoutMessagePlusGift.setEnabled(false);
		mLayoutFullScreenMask.setVisibility(View.GONE);
		Animation animation = AnimationUtils.loadAnimation(
				BaseMessageActivity.this, R.anim.controller_exit);
		animation.setInterpolator(AnimationUtils.loadInterpolator(
				BaseMessageActivity.this,
				android.R.anim.anticipate_interpolator));
		mLayoutMessagePlusBar.setAnimation(animation);
		mLayoutMessagePlusBar.setVisibility(View.GONE);
	}
    //屏幕底部滑动标志相关
//	protected void initRounds() {
//		mRoundsSelected = PhotoUtils.getRoundBitmap(BaseMessageActivity.this,
//				getResources().getColor(R.color.msg_short_line_selected));
//		mRoundsNormal = PhotoUtils.getRoundBitmap(BaseMessageActivity.this,
//				getResources().getColor(R.color.msg_short_line_normal));
//		for (int i = 0; i < mLayoutScroll.getChildCount(); i++) {
//			ImageView imageView = (ImageView) LayoutInflater.from(
//					BaseMessageActivity.this).inflate(
//					R.layout.include_message_shortline, null);
//			imageView.setImageBitmap(mRoundsNormal);
//			mLayoutRounds.addView(imageView);
//		}
//		((ImageView) mLayoutRounds.getChildAt(0))
//				.setImageBitmap(mRoundsSelected);
//	}
    //初始化右上角列表项PopupWindow
	protected void initPopupWindow() {
		mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				130, getResources().getDisplayMetrics());
		mHeaderHeight = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
						.getDisplayMetrics());
		mChatPopupWindow = new ChatPopupWindow(this, mWidth,
				LayoutParams.WRAP_CONTENT);
		mChatPopupWindow.setOnChatPopupItemClickListener(this);
	}

//	protected void initSynchronousDialog() {
////		mSynchronousDialog = BaseDialog.getDialog(BaseMessageActivity.this,
////				"提示", "成为陌陌会员即可同步好友聊天记录", "查看详情",
////				new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.dismiss();
////
////					}
////				}, "取消", new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.cancel();
////					}
////				});
////		mSynchronousDialog
////				.setButton1Background(R.drawable.btn_default_popsubmit);
//	}

//	protected class OnVoiceModeDialogItemClickListener implements
//			onSimpleListItemClickListener {
//
//		@Override
//		public void onItemClick(int position) {
//			mCheckId = position;
//		}
//	}
}
