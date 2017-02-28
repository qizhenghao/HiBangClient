package cn.hibang.liaohongxian.activity;


import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.register.RegisterIdActivity;

import com.example.newhaibang.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class WelcomeActivity extends BaseActivity {

	// private LinearLayout mLinearCtrlbar;
	// private LinearLayout mLinearAvatars;
	private Button mBtnRegister;
	private Button mBtnLogin;
	private ImageButton mIbtnAbout;

	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SMsgManage.getManager().setCurrContext(this);
		// full screen
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		findById();
		setListener();
		init();
	}

	private void init() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
		
	}

	private void setListener() {
		mBtnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WelcomeActivity.this.startActivity(new Intent(
						WelcomeActivity.this, RegisterIdActivity.class));
				WelcomeActivity.this.finish();
			}
		});

		mBtnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				WelcomeActivity.this.startActivity(intent2);
				WelcomeActivity.this.finish();

			}
		});

		mIbtnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				WelcomeActivity.this.startActivity(new Intent(
						WelcomeActivity.this, AboutActivity.class));
			}
		});
	}

	private void findById() {
		// mLinearCtrlbar = (LinearLayout)
		// findViewById(R.id.welcome_linear_ctrlbar);
		mBtnRegister = (Button) findViewById(R.id.welcome_btn_register);
		mBtnLogin = (Button) findViewById(R.id.welcome_btn_login);
		mIbtnAbout = (ImageButton) findViewById(R.id.welcome_ibtn_about);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * public void onClick(View v) { switch (v.getId()) {
	 * 
	 * case R.id.welcome_btn_register: Intent intent1 = new
	 * Intent(WelcomeActivity.this, RegisterActivity.class);
	 * WelcomeActivity.this.startActivity(intent1); break;
	 * 
	 * case R.id.welcome_btn_login: Intent intent2 = new
	 * Intent(WelcomeActivity.this, LoginActivity.class);
	 * WelcomeActivity.this.startActivity(intent2); break;
	 * 
	 * case R.id.welcome_ibtn_about: Intent intent3 = new
	 * Intent(WelcomeActivity.this, AboutActivity.class);
	 * WelcomeActivity.this.startActivity(intent3); break; } }
	 */

}
