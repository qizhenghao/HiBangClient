package cn.hibang.liuzhiwei.register;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.SRegisterMsgListener;
import cn.hibang.bruce.utils.MyToast;
import cn.hibang.huxing.clientmessage.CPhotoUpdateMsg;
import cn.hibang.huxing.clientmessage.CRegisterMsg;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.huxing.servermessage.SRegisterMsg;
import cn.hibang.liaohongxian.activity.LoginActivity;
import cn.hibang.liaohongxian.activity.RegisterActivity;
import cn.hibang.liaohongxian.activity.WelcomeActivity;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

import com.example.newhaibang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterIdActivity extends Activity implements OnClickListener,SRegisterMsgListener{
	
	private HeaderLayout mHeaderLayout;
	private EditText mEtMail;
	private EditText mEtPhone;
	private EditText mEtPassword;
	private EditText mEtPassword1;
    private Button mBtnNext;
    
	private LinearLayout mSeletePhoto;
	private LinearLayout mTakePhoto;
	private ImageView faceImage;
	private EditText mEtName;
	private EditText mEtAge;
	private RadioGroup mRgGender;
	private RelativeLayout mRlSchool;
	private HandyTextView mTvSchool;
	private Button mBtnPrevious;
	private String schoolName;
	
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	

	Bitmap myPhoto = null;
	HiBangUser user = null;
	private BaseApplication application = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_register);
		user = new HiBangUser();
		application = (BaseApplication) getApplication();
		
		SMsgManage.getManager().setsRegisterMsgListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.contextMap.put(Config.TAG_RegisterActivity, this);
		
		
		
		initViews();
		initEvents();
		init();
	}
	
	private void initViews()
	{
		mHeaderLayout = (HeaderLayout)findViewById(R.id.register_account_header);
		mHeaderLayout.mySettingTitle("账户注册");
		
		mEtMail = (EditText)findViewById(R.id.register_user_new_mail);
		mEtPhone = (EditText)findViewById(R.id.register_user_new_phone);
		mEtPassword = (EditText)findViewById(R.id.register_user_new_password);
		mEtPassword1 = (EditText)findViewById(R.id.register_user_repeat_password);
		mBtnPrevious = (Button)findViewById(R.id.reg_btn_previous);
		mBtnNext = (Button)findViewById(R.id.reg_btn_next);
		
		mSeletePhoto = (LinearLayout)findViewById(R.id.register_photo_layout_selectphoto);
		mTakePhoto = (LinearLayout)findViewById(R.id.register_photo_layout_takepicture);
		faceImage = (ImageView)findViewById(R.id.register_photo_iv_userphoto);
		
		mEtName = (EditText)findViewById(R.id.register_info_new_name);
		mEtAge = (EditText)findViewById(R.id.register_info_new_age);
		mRgGender = (RadioGroup)findViewById(R.id.register_user_info_gender);
		mRlSchool = (RelativeLayout)findViewById(R.id.register_school_layout_item);
		mTvSchool = (HandyTextView)findViewById(R.id.register_school_item_name);
	}
	
	private void initEvents()
	{
		mSeletePhoto.setOnClickListener(this);
		mTakePhoto.setOnClickListener(this);;
		mRlSchool.setOnClickListener(this);
		mBtnPrevious.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
	}
	
	private void init()
	{
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null)
		{
		schoolName = bundle.getString("school");
		mTvSchool.setText(schoolName);
		}
	}
	
	public void register() {
		// TODO Auto-generated method stub
		if (mEtMail.getText().toString().length() == 0
				|| !isEmail(mEtMail.getText().toString())) {
			showCustomToast("请输入正确的邮箱地址");
		}
		else if (mEtPassword.getText().toString().length() < 6) {
			
			showCustomToast("密码不能少于6位");
		}
		else if (!(mEtPassword.getText().toString().equals(mEtPassword1.getText().toString()))) {

			showCustomToast("两次输入的密码不一致，请重新输入");
		}
		else if (mEtPhone.getText().toString().length() == 0
				|| !isMobileNO(mEtPhone.getText().toString())) {
			showCustomToast("请输入正确的电话号码");

		}

		else if(mEtAge.getText().toString().length() == 0)
        {
        	showCustomToast("尚未填写年龄");
        }
		else if (mEtName.getText().toString().length() == 0) {
			showCustomToast("尚未填写姓名");
		}
		else if(mTvSchool.getText().toString() == "点击选择学校")
		{
			showCustomToast("尚未选择学校");
		}

		else
		{
			user.setEmail(mEtMail.getText().toString());
			user.setPassword(mEtPassword.getText().toString());
			user.setUsername(mEtName.getText().toString());
			user.setPhone(mEtPhone.getText().toString());
			
			CRegisterMsg msg = new CRegisterMsg();
			msg.setEmail(user.getEmail());
			msg.setPassword(user.getPassword());
			msg.setName(user.getUsername());
			msg.setPhone(user.getPhone());
			application.client.sendMessage(msg);
			
			
			//showCustomToast("注册成功");
		}

	}
	
	
	
	//判断邮箱格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	// 判断手机格式是否正确
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	
	public boolean hasSdcard() {
		// TODO Auto-generated method stub
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory() + "/"
									+ IMAGE_FILE_NAME);
					Uri uri = Uri.fromFile(tempFile);
					startPhotoZoom(uri);
				} else {
					Toast.makeText(RegisterIdActivity.this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			myPhoto = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(myPhoto);
			faceImage.setImageDrawable(drawable);
		}
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
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.reg_btn_previous:
			startActivity(new Intent(RegisterIdActivity.this,WelcomeActivity.class));
			break;
			
		case R.id.reg_btn_next:
			register();
			break;
			
		case R.id.register_photo_layout_selectphoto:
			Intent intentFromGallery = new Intent();
			intentFromGallery.setType("image/*"); // 设置文件类型
			intentFromGallery
					.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intentFromGallery,
					IMAGE_REQUEST_CODE);
			break;
		case R.id.register_photo_layout_takepicture:
			
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			// 判断存储卡是否可以用，可用进行存储
			if (hasSdcard()) {

				intentFromCapture.putExtra(
						MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(),
								IMAGE_FILE_NAME)));
			}

			startActivityForResult(intentFromCapture,
					CAMERA_REQUEST_CODE);
			break;
		case R.id.register_school_layout_item:
			startActivity(new Intent(RegisterIdActivity.this,RegProvinceActivity.class));
			break;

		default:
			break;
		}
		
	}

	
	 public byte[] Bitmap2Bytes(Bitmap bm) {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		 return baos.toByteArray();
	 }
	 
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(event.KEYCODE_BACK == keyCode) {
				startActivity(new Intent(this,WelcomeActivity.class));
				this.finish();
			}
			return super.onKeyDown(keyCode, event);
		}

	@Override
	public void onMsgReveived() {
		
	}

	@Override
	public void onMsgReveived(SRegisterMsg sRegisterMsg) {
		Message msg = new Message();
		msg.obj = sRegisterMsg;
		handler.sendMessage(msg);
	}
	 
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			HiBangUser user = ((SRegisterMsg)msg.obj).getUser();
			if (user.getUserID() == -1) {
				//MyToast.ShowMessage(RegisterIdActivity.this, "注册失败");
				showCustomToast("注册失败");
			} else {
				//MyToast.ShowMessage(RegisterIdActivity.this, "注册成功,请登录···" );
				showCustomToast("注册成功,请登录...");
				CPhotoUpdateMsg cPhotoRequestMsg = new CPhotoUpdateMsg();
				cPhotoRequestMsg.setUserId(user.getUserID());
				if(myPhoto != null) {
					cPhotoRequestMsg.setPhoto(Bitmap2Bytes(myPhoto));
					application.client.sendMessage(cPhotoRequestMsg);
				} 
				
				application.setUser(user);
				application.getHiBang().storeAccessToken(user,mEtPassword.getText().toString(), String.valueOf(user.hashCode()), System.currentTimeMillis()+7*24*60*60*1000);
				Intent intent = new Intent(RegisterIdActivity.this,LoginActivity.class);
				RegisterIdActivity.this.startActivity(intent);
				RegisterIdActivity.this.finish();
			}
		};
	};
	 
	
	@Override
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}
	
	
	
}
