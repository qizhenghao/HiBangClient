package cn.hibang.liuzhiwei.maintabs;

import java.util.List;

import com.example.newhaibang.R;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.bruce.interf.SQueryResultMsgListener;
import cn.hibang.bruce.interf.SRecommendListMsgListener;
import cn.hibang.bruce.utils.MyToast;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.msgutility.Requirement;
import cn.hibang.huxing.msgutility.UserRequirement;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.huxing.servermessage.SSelectReqMsg;
import cn.hibang.huxing.servermessage.SRecommendListMsg;
import cn.hibang.liaohongxian.activity.LoginActivity;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.android.BasePopupWindow.onSubmitClickListener;
import cn.hibang.liuzhiwei.popupwindow.NearByPopupWindow;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.HeaderSpinner;
import cn.hibang.liuzhiwei.view.HeaderLayout.HeaderStyle;
import cn.hibang.liuzhiwei.view.HeaderLayout.SearchState;
import cn.hibang.liuzhiwei.view.HeaderLayout.onMiddleImageButtonClickListener;
import cn.hibang.liuzhiwei.view.HeaderLayout.onSearchListener;
import cn.hibang.liuzhiwei.view.HeaderSpinner.onSpinnerClickListener;
import cn.hibang.liuzhiwei.view.SwitcherButton.SwitcherButtonState;
import cn.hibang.liuzhiwei.view.SwitcherButton.onSwitcherButtonClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class RecommendActivity extends BaseActivity implements
		SRecommendListMsgListener,SQueryResultMsgListener{

	private HeaderLayout mHeaderLayout;
	private HeaderSpinner mHeaderSpinner;
	private NearByPopupWindow mPopupWindow;
	private RecommendFragment mFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		SMsgManage.getManager().setsRlMsgListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.contextMap.put(Config.TAG_RecommendActivity, this);
		initPopupWindow();
		initViews();
		init();

	}
	
	@Override
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}

	public void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.nearby_header);
		mHeaderLayout.initSearch(new OnSearchClickListener());
		// mHeaderSpinner = mHeaderLayout.setTitleNearBy("附近",
		// new OnSpinnerClickListener(), "附近群组",
		// R.drawable.ic_topbar_search,
		// new OnMiddleImageButtonClickListener(), "个人", "群组",
		// new OnSwitcherButtonClickListener());

		mHeaderSpinner = mHeaderLayout.setTitleByMySelf("附近",
				new OnSpinnerClickListener(), "发现",
				R.drawable.ic_topbar_search,
				new OnMiddleImageButtonClickListener());

		mHeaderLayout.init(HeaderStyle.TITLE_MY_WATCH);
	}

	private void initPopupWindow() {

		mPopupWindow = new NearByPopupWindow(this);
		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {

			@Override
			public void onClick() {
				// mPeopleFragment.onManualRefresh();
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mHeaderSpinner.initSpinnerState(false);
			}
		});

	}

	private void init() {
		mFragment = new RecommendFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.recommend_layout_content, mFragment).commit();
	}

	public class OnSpinnerClickListener implements onSpinnerClickListener {

		@Override
		public void onClick(boolean isSelect) {

			if (isSelect) {
				mPopupWindow
						.showViewTopCenter(findViewById(R.id.nearby_layout_root));
			} else {
				mPopupWindow.dismiss();
			}

		}
	}

	public class OnSearchClickListener implements onSearchListener {

		@Override
		public void onSearch(EditText et) {

		}

	}

	public class OnMiddleImageButtonClickListener implements
			onMiddleImageButtonClickListener {

		@Override
		public void onClick() {
			mHeaderLayout.showSearch();
		}
	}

	public class OnSwitcherButtonClickListener implements
			onSwitcherButtonClickListener {

		@Override
		public void onClick(SwitcherButtonState state) {
			// FragmentTransaction ft = getSupportFragmentManager()
			// .beginTransaction();
			// ft.setCustomAnimations(R.anim.fragment_fadein,
			// R.anim.fragment_fadeout);
			switch (state) {
			case LEFT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
				// ft.replace(R.id.nearby_layout_content, mPeopleFragment)
				// .commit();
				break;

			case RIGHT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_GROUP);
				// ft.replace(R.id.nearby_layout_content,
				// mGroupFragment).commit();
				break;
			}
		}

	}

	public void onBackPressed() {
		if (mHeaderLayout.searchIsShowing()) {
			// clearAsyncTask();
			mHeaderLayout.dismissSearch();
			mHeaderLayout.clearSearch();
			mHeaderLayout.changeSearchState(SearchState.INPUT);
		} else {
			finish();
		}
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	private final int RECOMMEND_MSG_RECEIVED = 1;
	private final int QUERY_MSG_RECEIVED = 2;
	@Override
	public void onMsgReveived(SRecommendListMsg sRecommendListMsg) {
		Message msg = new Message();
		msg.what = this.RECOMMEND_MSG_RECEIVED;
		msg.obj = sRecommendListMsg;
		handler.sendMessage(msg);
	}
	@Override
	public void onQueryResultMsg(SSelectReqMsg sQueryResultMsg) {
		Message msg = new Message();
		msg.what = this.QUERY_MSG_RECEIVED;
		msg.obj = sQueryResultMsg;
		handler.sendMessage(msg);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case RECOMMEND_MSG_RECEIVED:
				List<UserRequirement> list = ((SRecommendListMsg) msg.obj).getRecommendList();
				//refresh the UI, For Liu zhi wei
				mFragment.setUserReqList(list);	
				break;
			case QUERY_MSG_RECEIVED:
				List<UserRequirement> queryList = ((SSelectReqMsg) msg.obj).getUserReqList();
				//refresh the UI, For Liu zhi wei
				mFragment.setUserReqList(queryList);	
				break;
			}
		}
	};


	private long mExitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
