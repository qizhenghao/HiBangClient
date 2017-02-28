package cn.hibang.liaohongxian.activity;

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
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.maintabs.MainTabActivity;
import cn.hibang.liuzhiwei.util.PhotoUtils;

import com.example.newhaibang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements SRegisterMsgListener{
	private Button backButton;
	private Button regButton;

	private RelativeLayout photo;
	private ImageView faceImage;

	private EditText email;
	private EditText name;
	private EditText password;
	private EditText ackPwd;
	private EditText setPhone;
	
	Bitmap myPhoto = null;
	HiBangUser user = null;
	private BaseApplication application = null;

	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		user = new HiBangUser();
		application = (BaseApplication) getApplication();
		
		SMsgManage.getManager().setsRegisterMsgListener(this);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.contextMap.put(Config.TAG_RegisterActivity, this);
		
		backButton = (Button) findViewById(R.id.reg_btn_previous);
		regButton = (Button) findViewById(R.id.reg_btn_next);

		photo = (RelativeLayout) findViewById(R.id.switch_face_rl);
		faceImage = (ImageView) findViewById(R.id.face);

		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		ackPwd = (EditText) findViewById(R.id.ackpwd);
		setPhone = (EditText) findViewById(R.id.setphone);

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(RegisterActivity.this)
						.setTitle("温馨提示")
						.setMessage("您还没有注册嗨帮，确定返回码？")
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												RegisterActivity.this,
												WelcomeActivity.class);
										RegisterActivity.this
												.startActivity(intent);
										RegisterActivity.this.finish();
									}
								})
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).create().show();

			}

		});

//		msg.setEmail("qizhenghao258@163.com");
//		msg.setPassword("123456");
//		msg.setName("齐总");
//		msg.setPhone("18215600693");
		
		regButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
		
				
				if(register()) {										
					CRegisterMsg msg = new CRegisterMsg();
					msg.setEmail(user.getEmail());
					msg.setPassword(user.getPassword());
					msg.setName(user.getUsername());
					msg.setPhone(user.getPhone());
					application.client.sendMessage(msg);									
				}
			}
		});

		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setPhoto();
			}

		});

	}

	public boolean register() {
		// TODO Auto-generated method stub
		if (email.getText().toString().length() == 0
				|| !isEmail(email.getText().toString())) {
			new AlertDialog.Builder(RegisterActivity.this).setTitle("提示")
					.setMessage("请输入正确的邮箱地址").setPositiveButton("确认", null)
					.show();
			return false;
		} else {
			user.setEmail(email.getText().toString());
		}
		if (password.getText().toString().length() < 6) {
			Toast.makeText(RegisterActivity.this, "密码不能少于6位", 1).show();
			return false;
		} 
		if (!(password.getText().toString().equals(ackPwd.getText().toString()))) {
			new AlertDialog.Builder(RegisterActivity.this).setTitle("提示")
					.setMessage("两次输入的密码不一致，请重新输入")
					.setPositiveButton("确认", null).show();
			return false;
		} else {
			user.setPassword(password.getText().toString());
		}
		if (name.getText().toString().length() == 0) {
			Toast.makeText(RegisterActivity.this, "昵称不能为空", 1).show();
			return false;
		} else{
			user.setUsername(name.getText().toString());
		}
		if (setPhone.getText().toString().length() == 0
				|| !isMobileNO(setPhone.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "请输入正确的电话号码", 1).show();
			return false;
		} else {
			user.setPhone(setPhone.getText().toString());
		}
		
		return true;
	}

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

	private void setPhoto() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

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

						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

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
					Toast.makeText(RegisterActivity.this, "未找到存储卡，无法存储照片！",
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
				MyToast.ShowMessage(RegisterActivity.this, "注册失败");
			} else {
				MyToast.ShowMessage(RegisterActivity.this, "注册成功,请登录···" );
				
				CPhotoUpdateMsg cPhotoRequestMsg = new CPhotoUpdateMsg();
				cPhotoRequestMsg.setUserId(user.getUserID());
				if(myPhoto != null) {
					cPhotoRequestMsg.setPhoto(PhotoUtils.Bitmap2Bytes(myPhoto));
					application.client.sendMessage(cPhotoRequestMsg);
				} 
				
				application.setUser(user);
				application.getHiBang().storeAccessToken(user,password.getText().toString(), String.valueOf(user.hashCode()), System.currentTimeMillis()+7*24*60*60*1000);
				Intent intent = new Intent(RegisterActivity.this,MainTabActivity.class);
				RegisterActivity.this.startActivity(intent);
				RegisterActivity.this.finish();
			}
		};
	};
	 
	
	@Override
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

}
