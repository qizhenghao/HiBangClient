package cn.hibang.liaohongxian.activity;

import java.util.regex.Pattern;

import com.example.newhaibang.R;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.SLoginMsgListener;
import cn.hibang.huxing.clientmessage.CLoginMsg;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.maintabs.MainTabActivity;
import cn.hibang.liuzhiwei.maintabs.MessageActivity;
import cn.hibang.liuzhiwei.view.HandyTextView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements SLoginMsgListener {

	private EditText mEtAccount;
	private EditText mEtPwd;
	private Button mBtnBack;
	private Button mBtnLogin;

	/*
	 * private static final String[] DEFAULT_ACCOUNTS = new String[] {
	 * "+8612345678901", "86930007@qq.com", "86930007" }; private static final
	 * String DEFAULT_PASSWORD = "123456"; private String mAreaCode = "+86";
	 */
	private String mAccount;
	private String mPassword;

	BaseApplication application = null;

	// private SimpleListDialog mSimpleListDialog;
	private String[] mCountryCodes;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.activity_login);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.getManager().setsLoginMsgListener(this);
		SMsgManage.contextMap.put(Config.TAG_LoginActivity, this);

		application = (BaseApplication) getApplication();
		mEtAccount = (EditText) findViewById(R.id.login_et_account);
		mEtPwd = (EditText) findViewById(R.id.login_edt_pwd);
		mBtnBack = (Button) findViewById(R.id.login_btn_back);
		mBtnLogin = (Button) findViewById(R.id.login_btn_login);

		mBtnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(LoginActivity.this,
						WelcomeActivity.class);
				LoginActivity.this.startActivity(intent1);
			}
		});

		mBtnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				showCustomToast("···登陆中···");
				showLoadingDialog("登陆");
				login();
//				LoginActivity.this.startActivity(new Intent(LoginActivity.this,MainTabActivity.class));
//				LoginActivity.this.finish();
			}
		});
	}


	@Override
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}
	private boolean matchEmail(String text) {
		if (Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text)
				.matches()) {
			return true;
		}
		return false;
	}

	private boolean matchPhone(String text) {
		if (Pattern.compile("(\\d{11})|(\\+\\d{3,})").matcher(text).matches()) {
			return true;
		}
		return false;
	}

	private boolean matchMoMo(String text) {
		if (Pattern.compile("\\d{7,9}").matcher(text).matches()) {
			return true;
		}
		return false;
	}

	private boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}

	private boolean validateAccount() {
		mAccount = null;
		if (isNull(mEtAccount)) {
			Toast.makeText(getApplicationContext(), "请输入手机号码或登录邮箱",
					Toast.LENGTH_SHORT).show();
			mEtAccount.requestFocus();
			return false;
		}
		String account = mEtAccount.getText().toString().trim();
		if (matchPhone(account)) {
			if (account.length() < 3) {
				 showCustomToast("账号格式不正确");
//				Toast.makeText(getApplicationContext(), "账号格式不正确",
//						Toast.LENGTH_SHORT).show();
				mEtAccount.requestFocus();
				return false;
			}
			if (Pattern.compile("(\\d{3,})|(\\+\\d{3,})").matcher(account)
					.matches()) {
				mAccount = account;
				return true;
			}
		}
		if (matchEmail(account)) {
			mAccount = account;
			return true;
		}
		if (matchMoMo(account)) {
			mAccount = account;
			return true;
		}
		showCustomToast("账号格式不正确");
//		Toast.makeText(getApplicationContext(), "账号格式不正确", Toast.LENGTH_SHORT)
//				.show();
		mEtAccount.requestFocus();
		return false;
	}

	private boolean validatePwd() {
		mPassword = null;
		String pwd = mEtPwd.getText().toString().trim();
		if (pwd.length() < 4) {
			// showCustomToast("密码不能小于4位");
//			Toast.makeText(getApplicationContext(), "密码不能小于4位",
//					Toast.LENGTH_SHORT).show();
			showCustomToast("密码不能小于4位");
			mEtPwd.requestFocus();
			return false;
		}
		if (pwd.length() > 16) {
			// showCustomToast("密码不能大于16位");
//			Toast.makeText(getApplicationContext(), "密码不能大于16位",
//					Toast.LENGTH_SHORT).show();
			showCustomToast("密码不能大于16位");
			mEtPwd.requestFocus();
			return false;
		}
		mPassword = pwd;
		return true;
	}

	private void login() {
//		 if ((!validateAccount()) || (!validatePwd())) {
//		 return;
//		 }
		CLoginMsg msg = new CLoginMsg();
//		msg.setPhoneNumber("孟蓉蓉");
//		msg.setPassword("123456");
		//msg.setPhoneNumber("NewBee");
		//msg.setPassword("123456");
	//	msg.setPhoneNumber("lzw");
	//	msg.setPassword("qqqqqq");
		msg.setPhoneNumber(mEtAccount.getText().toString());
		msg.setPassword(mEtPwd.getText().toString());
//		msg.setPhoneNumber(mEtAccount.getText().toString());
//		msg.setPassword(mEtPwd.getText().toString());
		application.client.sendMessage(msg);
		
	}
	
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			HiBangUser user = ((SLoginMsg)msg.obj).getUser();
			if (user.getUserID() == -1) {
				//MyToast.ShowMessage(LoginActivity.this, "登录失败,账号或密码错误");
				showCustomToast("登录失败,账号或密码错误");
				closeLoadingDialog();
			} else if(user.getUserID() == -2) { 
			//	MyToast.ShowMessage(LoginActivity.this, "登录失败,重复登录！");
				showCustomToast("登录失败,重复登录!");
				closeLoadingDialog();
			}
			else {
				//MyToast.ShowMessage(LoginActivity.this, "登陆成功" );
				showCustomToast("登陆成功!");
				closeLoadingDialog();
				application.setUser(user);
				String psd = mEtPwd.getText().toString(); 
				application.getHiBang().storeAccessToken(user, psd, String.valueOf(user.hashCode()), System.currentTimeMillis()+7*24*60*60*1000);
				Intent intent = new Intent(LoginActivity.this,MainTabActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("className", MessageActivity.class.getName());
				intent.putExtras(bundle);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
			}
		};
	};
	
	
	@Override
	public void onSLoginMsgReveived(SLoginMsg sMsg) {
		Message msg = new Message();
		msg.obj = sMsg;
		handler.sendMessage(msg);
	}
	
	
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}


	@Override
	protected void initViews() {
		
	}


	@Override
	protected void initEvents() {
		
	}

}
