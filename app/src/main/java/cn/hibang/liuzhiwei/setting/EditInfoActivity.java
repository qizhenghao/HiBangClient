package cn.hibang.liuzhiwei.setting;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.newhaibang.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
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
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CPhotoUpdateMsg;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

public class EditInfoActivity extends BaseActivity implements OnClickListener{
	
	private HeaderLayout mHeaderLayout;
	private LinearLayout mIvBack;
	private LinearLayout mSeletePhoto;
	private LinearLayout mTakePhoto;
	private ImageView faceImage;
	private EditText mEtName;
	private EditText mEtPhone;
	private EditText mEtSign;
	private EditText mEtMail;
	private EditText mEtAge;
	private RadioGroup mRgGender;
	private Button mBtnSubit;
	private RelativeLayout mRlSchool;
	private HandyTextView mTvSchool;
	private HiBangUser user;
	
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	
	private String schoolName;
	private Bitmap portrait = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		mApplication = (BaseApplication) getApplication();
		user = mApplication.getUser();
		setContentView(R.layout.activity_setting_info_edit);
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {
		
		mHeaderLayout = (HeaderLayout)findViewById(R.id.setting_edit_info_header);
		mHeaderLayout.myBackSettingTitle("资料");
		mHeaderLayout.mySettingMidTitle("资料设置");
		
		mIvBack = (LinearLayout)findViewById(R.id.header_iv_setting_back);
		mSeletePhoto = (LinearLayout)findViewById(R.id.setting_photo_layout_selectphoto);
		mTakePhoto = (LinearLayout)findViewById(R.id.setting_photo_layout_takepicture);
		faceImage = (ImageView)findViewById(R.id.setting_photo_iv_userphoto);
		
		mEtName = (EditText)findViewById(R.id.setting_et_new_name);
		mEtPhone = (EditText)findViewById(R.id.setting_et_new_phone);
		mEtSign = (EditText)findViewById(R.id.setting_et_new_sign);
		mEtMail = (EditText)findViewById(R.id.setting_et_new_mail);
		mEtAge = (EditText)findViewById(R.id.setting_et_new_age);
		mRgGender = (RadioGroup)findViewById(R.id.setting_user_info_gender);
		mBtnSubit = (Button)findViewById(R.id.setting_user_info_edit_btn);
		mRlSchool = (RelativeLayout)findViewById(R.id.setting_school_layout_item);
		mTvSchool = (HandyTextView)findViewById(R.id.setting_school_item_name);
	}

	@Override
	protected void initEvents() {
		mIvBack.setOnClickListener(this);
		mSeletePhoto.setOnClickListener(this);
		mTakePhoto.setOnClickListener(this);
		mBtnSubit.setOnClickListener(this);
		mRlSchool.setOnClickListener(this);
		
	}
	
	private void init()
	{
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null)
		{
		schoolName = bundle.getString("school");
		mTvSchool.setText(schoolName);
		}
		
		
		//从数据库中获取用户头像
//		String path = DBManage.getPhotoPathById(user.getUserID());
//
//		if (path == null  || path.equals("") ) {
//			//mIvPhotoView.setImageResource(R.drawable.my_test_chating_image);
//			CPhotoRequestMsg msg = new CPhotoRequestMsg();
//			msg.setUserID(user.getUserID());
//			mApplication.client.sendMessage(msg);
//		} else {
//			try {
//				faceImage.setImageBitmap(BitmapFactory
//						.decodeStream(new FileInputStream(path)));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		
		faceImage.setImageBitmap(PhotoUtils.getPortraitById(user.getUserID(), mApplication));
		
		
		mEtName.setText(user.getUsername());
		mEtPhone.setText(user.getPhone());
		mEtSign.setText(user.getPs());
		mEtMail.setText(user.getEmail());
		mEtAge.setText(user.getUserAge().toString());
		
		if(user.getGender() == GENDER.FEMALE)
		{
			mRgGender.check(R.id.setting_user_info_female);
		}
		else
		{
			mRgGender.check(R.id.setting_user_info_male); 
		}
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
					Toast.makeText(EditInfoActivity.this, "未找到存储卡，无法存储照片！",
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
	@SuppressWarnings("deprecation")
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			portrait = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(portrait);
			faceImage.setImageDrawable(drawable);
		}
	}
	
	

	public void register() {
		// TODO Auto-generated method stub
		if (mEtMail.getText().toString().length() == 0
				|| !isEmail(mEtMail.getText().toString())) {
			showCustomToast("请输入正确的邮箱地址");
		}
//		if (password.getText().toString().length() < 6) {
//			Toast.makeText(RegisterActivity.this, "密码不能少于6位", 1).show();
//		}
//		if (!(password.getText().toString().equals(ackPwd.getText().toString()))) {
//			new AlertDialog.Builder(RegisterActivity.this).setTitle("提示")
//					.setMessage("两次输入的密码不一致，请重新输入")
//					.setPositiveButton("确认", null).show();
//		}
		else if (mEtName.getText().toString().length() == 0) {
			showCustomToast("姓名不能为空");
		}
		else if (mEtPhone.getText().toString().length() == 0
				|| !isMobileNO(mEtPhone.getText().toString())) {
			showCustomToast("请输入正确的电话号码");

		}
		else
		{
			CPhotoUpdateMsg msg = new CPhotoUpdateMsg();
			if(portrait!=null) {
				msg.setPhoto(PhotoUtils.Bitmap2Bytes(portrait));
				msg.setUserId(user.getUserID());
				mApplication.client.sendMessage(msg);
			}
			showCustomToast("信息修改成功");
		}

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
		case R.id.header_iv_setting_back:
			startActivity(new Intent(this,PersonInfoActivity.class));
			this.finish();
		
			break;
		case R.id.setting_photo_layout_selectphoto:
			Intent intentFromGallery = new Intent();
			intentFromGallery.setType("image/*"); // 设置文件类型
			intentFromGallery
					.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intentFromGallery,
					IMAGE_REQUEST_CODE);
			break;
			
		case R.id.setting_photo_layout_takepicture:
			
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
		case R.id.setting_user_info_edit_btn:
			register();
			break;
		case R.id.setting_school_layout_item:
			startActivity(new Intent(EditInfoActivity.this,EditProvinceActivity.class));
			this.finish();
			break;

		default:
			break;
		}
	
		
	}

}
