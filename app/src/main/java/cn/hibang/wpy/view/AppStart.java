package cn.hibang.wpy.view;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CLoginMsg;
import cn.hibang.liuzhiwei.maintabs.MainTabActivity;
import com.example.newhaibang.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class AppStart extends Activity implements Runnable {
	private BaseApplication application = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.appstart);
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
			AppStart.this.startActivity(new Intent(AppStart.this,
					MainTabActivity.class));
			AppStart.this.finish();
		} else {
			AppStart.this.startActivity(new Intent(AppStart.this,
					Whatsnew.class));
			AppStart.this.finish();
		}

	}
}