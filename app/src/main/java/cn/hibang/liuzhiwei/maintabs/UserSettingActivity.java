package cn.hibang.liuzhiwei.maintabs;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.HeaderLayout;

import com.example.newhaibang.R;

public class UserSettingActivity extends BaseActivity{
	
	private HeaderLayout mHeaderLayout;
	private UserSettingFragment mFragment;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting);
		initViews();
		init();
	}
	
	public void initViews()
	{
		mHeaderLayout = (HeaderLayout) findViewById(R.id.usersetting_header);
		mHeaderLayout.mySettingTitle("设置");
	}
	
	
	public void init()
	{
		mFragment = new UserSettingFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.usersetting_layout_content, mFragment).commit();
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
