package cn.hibang.liuzhiwei.maintabs;


import java.util.List;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.SFriendListListener;
import cn.hibang.bruce.interf.SPhotoRequestMsgListener;
import cn.hibang.huxing.msgutility.FriendState;
import cn.hibang.huxing.msgutility.HelpRelation;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.android.BasePopupWindow.onFinishClickListener;
import cn.hibang.liuzhiwei.android.BasePopupWindow.onSubmitClickListener;
import cn.hibang.liuzhiwei.popupwindow.PublishSelectPopupWindow;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.HeaderSpinner;
import cn.hibang.liuzhiwei.view.HeaderLayout.HeaderStyle;
import cn.hibang.liuzhiwei.view.HeaderSpinner.onSpinnerClickListener;
import cn.hibang.liuzhiwei.view.SwitcherButton.SwitcherButtonState;
import cn.hibang.liuzhiwei.view.SwitcherButton.onSwitcherButtonClickListener;

import com.example.newhaibang.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class FriendActivity extends BaseActivity implements SFriendListListener,SPhotoRequestMsgListener{
	
	private HeaderLayout mHeaderLayout;
	private HeaderSpinner mHeaderSpinner;
	private HelpMeFriendFragment mFriendFragment;
	private MeHelpFriendFragment mHelperFragment;
	private PublishSelectPopupWindow mPopupWindow;
	
    private int mFriendCount1 = 1;
	private int mFriendCount2 = 1;
    private int mHelperCount1 = 1;
	private int mHelperCount2 = 1;
	


	List<HiBangUser> mFriendList1 = null;
	List<HiBangUser> mFriendList2 = null;
	List<HiBangUser> mHelperList1 = null;
	List<HiBangUser> mHelperList2 = null;
	
	boolean currentFragment = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		System.out.println("friendActivity的onCreate调用");
		setContentView(R.layout.activity_friend);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.getManager().setFriendListListener(this);
		SMsgManage.getManager().setsPhotoRequestMsgListener(this);
		SMsgManage.contextMap.put(Config.TAG_FriendActivity, this);
		initPopupWindow();
		initViews();
		init();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SMsgManage.getManager().setCurrContext(this);
		System.out.println("friendActivity的onresume调用");
		mHeaderLayout.changeMiddleTitle(" 交易中");

		
	}
	
	//初始化视图
	public void initViews()
	{
		mHeaderLayout = (HeaderLayout) findViewById(R.id.friend_header);
		//mHeaderLayout.myFriendTitle("好友", "我帮", "帮我", new OnSwitcherButtonClickListener());
		mHeaderLayout.init(HeaderStyle.TITLE_MY_FRIEND);
		mHeaderSpinner = mHeaderLayout.setFriendTitle("好友"," 交易中", new OnSpinnerClickListener(),
				"帮我", "我帮", new OnSwitcherButtonClickListener());
	}
	
	public void init()
	{
		mFriendFragment = new HelpMeFriendFragment(mApplication, this, this);
		mHelperFragment = new MeHelpFriendFragment(mApplication, this, this);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.myfriend_layout_content, mFriendFragment).commit();
		currentFragment = false;
		//从数据库中获取存有四种好友状态的list
//		DBManage.context = FriendActivity.this;
		
//		mFriendList1 = FriendDB.newInstance(FriendActivity.this).getFriendByCount(mFriendCount1++,FriendState.TRADING,HelpRelation.HELP_ME);
		
		mFriendList1 = DBManage.getFriendByCount(mFriendCount1++,FriendState.TRADING,HelpRelation.HELP_ME);
		mFriendList2 = DBManage.getFriendByCount(mFriendCount2++,FriendState.SUCCESS,HelpRelation.HELP_ME);		
		mHelperList1 = DBManage.getFriendByCount(mHelperCount1++,FriendState.TRADING,HelpRelation.ME_HELP);
		mHelperList2 = DBManage.getFriendByCount(mHelperCount2++,FriendState.SUCCESS,HelpRelation.ME_HELP);
        
		mFriendFragment.setHiBangUserList(mFriendList1, mFriendList2);
		mHelperFragment.setHiBangUserList(mHelperList1, mHelperList2);
	   
		
	}

	//PopupWindow对话框初始化
	private void initPopupWindow()
	{

		mPopupWindow = new PublishSelectPopupWindow(this);
		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {

			@Override
			public void onClick() {
			//	mFriendFragment.onManualRefresh();
		 
		     mHeaderLayout.changeMiddleTitle(" 交易中");
		     if(!currentFragment)
		     mFriendFragment.updateTradingAdapter();
		     else
		     mHelperFragment.updateTradingAdapter();
			}
		});
		
		mPopupWindow.setOnFinishClickListener(new onFinishClickListener() {

			@Override
			public void onClick() {
			//	mFriendFragment.onManualRefresh();			 
		     mHeaderLayout.changeMiddleTitle("交易完成");
		     if(!currentFragment)
		     mFriendFragment.updateSuccessAdapter();
		     else
		     mHelperFragment.updateSuccessAdapter();
			}
		});
		
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mHeaderSpinner.initSpinnerState(false);
			}
		});
	
	}

	//点击下拉选择对话框按钮监听
	public class OnSpinnerClickListener implements onSpinnerClickListener {

		@Override
		public void onClick(boolean isSelect) {
			
			if (isSelect) {
				mPopupWindow.showViewTopCenter(findViewById(R.id.nearby_layout_root));
			} else {
				mPopupWindow.dismiss();
			}

		}
	}
	
	
	public class OnSwitcherButtonClickListener implements
	onSwitcherButtonClickListener {

    @Override
    public void onClick(SwitcherButtonState state) {
	FragmentTransaction ft = getSupportFragmentManager()
			.beginTransaction();
	ft.setCustomAnimations(R.anim.fragment_fadein,
			R.anim.fragment_fadeout);
	switch (state) {
	case LEFT:
		//mHeaderLayout.myFriendTitle("好友", "我帮", "帮我", new OnSwitcherButtonClickListener());
		ft.replace(R.id.myfriend_layout_content, mFriendFragment)
				.commit();
		currentFragment = false;
		mHeaderLayout.changeMiddleTitle(" 交易中");
		break;

	case RIGHT:
	//	mHeaderLayout.myFriendTitle("好友", "我帮", "帮我", new OnSwitcherButtonClickListener());
		ft.replace(R.id.myfriend_layout_content, mHelperFragment).commit();
	    mHeaderLayout.changeMiddleTitle(" 交易中");
		currentFragment = true;
		break;
	}
}

}


	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

	private final int MSG_RECEIVED = 1;
	private final int PHOTO_MSG_RECEIVED = 2;
	@Override
	public void onMsgReveived(List<HiBangUser> list) {
		Message msg = new Message();
		msg.what = this.MSG_RECEIVED;
		msg.obj = list;
		handler.sendMessage(msg);
		
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_RECEIVED:
				List<HiBangUser> list = (List<HiBangUser>) msg.obj;
				judgeFriendState(list);				
				//refresh the UI, For Liu zhi wei	
				//更新帮我Fragment
				mFriendFragment.setHiBangUserList(mFriendList1, mFriendList2);
				mHelperFragment.setHiBangUserList(mHelperList1, mHelperList2);
				
				  if(!currentFragment)
				  {
					if(	!mHeaderLayout.checkMiddleTitleState())
						mFriendFragment.updateTradingAdapter();
					else 
						mFriendFragment.updateSuccessAdapter();
				  }
				  else
				  {
					if(	!mHeaderLayout.checkMiddleTitleState())
						mHelperFragment.updateTradingAdapter();
					else 
						mHelperFragment.updateSuccessAdapter();
				  }
			case PHOTO_MSG_RECEIVED:
				 if(!currentFragment)
				  {
					if(	!mHeaderLayout.checkMiddleTitleState())
						mFriendFragment.updateTradingAdapter();
					else 
						mFriendFragment.updateSuccessAdapter();
				  }
				  else
				  {
					if(	!mHeaderLayout.checkMiddleTitleState())
						mHelperFragment.updateTradingAdapter();
					else 
						mHelperFragment.updateSuccessAdapter();
				  }
	             
			break;
				
			}
		}
	};
	
	public void judgeFriendState(List<HiBangUser> list)
	{
		mFriendList1.clear();
		mFriendList2.clear();
		mHelperList1.clear();
		mHelperList2.clear();
	    for(int i = 0;i <list.size();i++)
	    {
	    	if(list.get(i).getFriendState() == FriendState.TRADING && list.get(i).getHelpRel() ==HelpRelation.HELP_ME)
	    		mFriendList1.add(list.get(i));
	    	else if(list.get(i).getFriendState() == FriendState.SUCCESS && list.get(i).getHelpRel() ==HelpRelation.HELP_ME)
	    		mFriendList2.add(list.get(i));
	    	else if(list.get(i).getFriendState() == FriendState.TRADING && list.get(i).getHelpRel() ==HelpRelation.ME_HELP)
	    		mHelperList1.add(list.get(i));
	    	else if(list.get(i).getFriendState() == FriendState.SUCCESS && list.get(i).getHelpRel() ==HelpRelation.ME_HELP)
	    		mHelperList2.add(list.get(i));
	    }
	}

	@Override
	public void onSPhotoReqMsgReveived() {
		Message msg = new Message();
		msg.what = this.PHOTO_MSG_RECEIVED;
		handler.sendMessage(msg);
	}
	
	
	
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
	
	

	


