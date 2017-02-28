package cn.hibang.liuzhiwei.register;

import java.io.File;

import cn.hibang.liuzhiwei.setting.EditInfoActivity;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

import com.example.newhaibang.R;

import android.app.Activity;
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

public class RegisterInfoActivity extends Activity implements OnClickListener{
	
	private HeaderLayout mHeaderLayout;
	private LinearLayout mSeletePhoto;
	private LinearLayout mTakePhoto;
	private ImageView faceImage;
	private EditText mEtName;
	private EditText mEtAge;
	private RadioGroup mRgGender;
	private RelativeLayout mRlSchool;
	private HandyTextView mTvSchool;
	private Button mBtnPrevious;
	private Button mBtnFinish;
	private String schoolName;
	
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_register);
		initViews();
		initEvents();
		init();
	}
	
	public void initViews()
	{
		mHeaderLayout = (HeaderLayout)findViewById(R.id.register_info_header);
		mHeaderLayout.mySettingTitle("个人资料");
		
		mSeletePhoto = (LinearLayout)findViewById(R.id.register_photo_layout_selectphoto);
		mTakePhoto = (LinearLayout)findViewById(R.id.register_photo_layout_takepicture);
		faceImage = (ImageView)findViewById(R.id.register_photo_iv_userphoto);
		
		mEtName = (EditText)findViewById(R.id.register_info_new_name);
		mEtAge = (EditText)findViewById(R.id.register_info_new_age);
		mRgGender = (RadioGroup)findViewById(R.id.register_user_info_gender);
		mRlSchool = (RelativeLayout)findViewById(R.id.register_school_layout_item);
		mTvSchool = (HandyTextView)findViewById(R.id.register_school_item_name);
		mBtnPrevious = (Button)findViewById(R.id.reg_info_btn_previous);
		mBtnFinish = (Button)findViewById(R.id.reg_info_btn_next);
	}
	
	public void initEvents()
	{
		mSeletePhoto.setOnClickListener(this);
		mTakePhoto.setOnClickListener(this);;
		mRlSchool.setOnClickListener(this);
		mBtnPrevious.setOnClickListener(this);
		mBtnFinish.setOnClickListener(this);
	}
	
	public void init()
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
        if(mEtAge.getText().toString().length() == 0)
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
			showCustomToast("注册成功");
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
					Toast.makeText(RegisterInfoActivity.this, "未找到存储卡，无法存储照片！",
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
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageDrawable(drawable);
		}
	}


	@Override
	public void onClick(View v) {
	  switch (v.getId()) {
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
		startActivity(new Intent(RegisterInfoActivity.this,RegProvinceActivity.class));
		break;
	case R.id.reg_info_btn_previous:
		startActivity(new Intent(RegisterInfoActivity.this,RegisterIdActivity.class));
		this.finish();
		break;
	case R.id.reg_info_btn_next:
		register();
		break;

	default:
		break;
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

	

}
