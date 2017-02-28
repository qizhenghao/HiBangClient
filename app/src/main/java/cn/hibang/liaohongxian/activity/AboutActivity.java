package cn.hibang.liaohongxian.activity;

import com.example.newhaibang.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity {

	private Button mBtnAboutHibang = null;
	private Button mBtnHelp = null;
	private Button mBtnCheckForUpdate = null;
	private Button mBtnVersion = null;
	private Button mBtnUserProtocol = null;
	
	//HandyTextView mHtvVersionName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
//		mHtvVersionName = (HandyTextView) findViewById(R.id.about_htv_versionname);
//		try {
//			PackageInfo packageInfo = getPackageManager().getPackageInfo(
//					getPackageName(), 0);
//			mHtvVersionName.setText("鐗堟湰: Android " + packageInfo.versionName);
//		} catch (NameNotFoundException e) {
//			mHtvVersionName.setVisibility(View.GONE);
//		}
		mBtnAboutHibang = (Button) findViewById(R.id.about_btn_abouthibang);
		mBtnHelp = (Button) findViewById(R.id.about_btn_help);
		mBtnCheckForUpdate = (Button) findViewById(R.id.about_btn_checkforupdate);
		mBtnVersion = (Button) findViewById(R.id.about_btn_version);
		mBtnUserProtocol = (Button) findViewById(R.id.about_btn_userprotocol);
		
		
		mBtnCheckForUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		
		mBtnUserProtocol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		
		mBtnVersion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		mBtnAboutHibang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mBtnHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.KEYCODE_BACK == keyCode) {
//			startActivity(new Intent(this,WelcomeActivity.class));
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

