package cn.hibang.liaohongxian.activity;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.service.NettyService;
import cn.hibang.huxing.clientmessage.CLoginMsg;
import cn.hibang.liuzhiwei.maintabs.MainTabActivity;

import com.example.newhaibang.R;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Welcome extends Activity implements Runnable{

	private BaseApplication application = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// full screen
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		application = (BaseApplication) getApplication();
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (application.getHiBang().isAccessTokenExist()) {
			CLoginMsg msg = new CLoginMsg();
			application.setUser(application.getHiBang().getUser());
			msg.setPhoneNumber(application.getHiBang().getUser().getUsername());
			msg.setPassword(application.getHiBang().getUser().getPassword());
			application.client.sendMessage(msg);
			Welcome.this.startActivity(new Intent(
					Welcome.this, MainTabActivity.class));
			Welcome.this.finish();
		} else {
			Welcome.this.startActivity(new Intent(
					Welcome.this, WelcomeActivity.class));
			Welcome.this.finish();
		}
		
		
	}
	


}
