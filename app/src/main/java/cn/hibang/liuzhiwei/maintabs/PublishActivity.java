package cn.hibang.liuzhiwei.maintabs;

import java.util.List;

import com.example.newhaibang.R;

import cn.hibang.bruce.domain.MyPubHelpMe;
import cn.hibang.bruce.domain.MyPubMeHelp;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.SwitcherButton.SwitcherButtonState;
import cn.hibang.liuzhiwei.view.SwitcherButton.onSwitcherButtonClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

public class PublishActivity extends BaseActivity{
	
	private HeaderLayout mHeaderLayout;
//	private HeaderSpinner mHeaderSpinner;
//	private PublishSelectPopupWindow mPopupWindow;
	private PublishFragment mPublishFragment;
	private HelperPublishFragment mHelperFragment;
	
	List<MyPubHelpMe> helpMeList = null;
	List<MyPubMeHelp> meHelpList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);
		//initPopupWindow();
		initViews();
		init();
		
	}
	
	public void initViews()
	{
		mHeaderLayout = (HeaderLayout) findViewById(R.id.publish_header);
//		mHeaderLayout.init(HeaderStyle.TITLE_MY_PUBLISH);
//		mHeaderSpinner = mHeaderLayout.setPublishTitle("发布","全部", new OnSpinnerClickListener(),
//				"我帮", "帮我", new OnSwitcherButtonClickListener());
		mHeaderLayout.myPublishTitle("发布", "帮我", "我帮", new OnSwitcherButtonClickListener());
		
	}
	
	public void init()
	{
		mPublishFragment = new PublishFragment(mApplication, this, this);
		mHelperFragment = new HelperPublishFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.publish_layout_content, mPublishFragment).commit();
		
		helpMeList = DBManage.getAllCPubHelpMe();
		meHelpList = DBManage.getAllCPubMeHelp();
	}

//	//PopupWindow对话框初始化
//	private void initPopupWindow()
//	{
//
//		mPopupWindow = new PublishSelectPopupWindow(this);
//		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {
//
//			@Override
//			public void onClick() {
//			//	mPeopleFragment.onManualRefresh();
//			}
//		});
//		mPopupWindow.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				mHeaderSpinner.initSpinnerState(false);
//			}
//		});
//	
//	}
//
//	//点击下拉选择对话框按钮监听
//	public class OnSpinnerClickListener implements onSpinnerClickListener {
//
//		@Override
//		public void onClick(boolean isSelect) {
//			
//			if (isSelect) {
//				mPopupWindow
//						.showViewTopCenter(findViewById(R.id.nearby_layout_root));
//			} else {
//				mPopupWindow.dismiss();
//			}
//
//		}
//	}
//	
	//我帮和帮我的选择按钮监听
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
		//mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
		ft.replace(R.id.publish_layout_content, mPublishFragment)
				.commit();
		break;

	case RIGHT:
		//mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_GROUP);
		ft.replace(R.id.publish_layout_content, mHelperFragment).commit();
		break;
	}
}

}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
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
