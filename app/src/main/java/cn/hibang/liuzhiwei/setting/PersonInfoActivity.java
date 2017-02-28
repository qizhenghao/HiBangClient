package cn.hibang.liuzhiwei.setting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

import com.example.newhaibang.R;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PersonInfoActivity extends BaseActivity implements OnClickListener{
	
	private HeaderLayout mHeaderLayout;
	private LinearLayout mIvBack;
	private Button mEditBtn;
	private BaseApplication mApplication;
	private HiBangUser user;
	
	private ImageView mAvatar;//头像
    private HandyTextView mHtvName;//姓名
	private LinearLayout mLayoutGender;// 性别根布局
	private ImageView mIvGender;// 性别
	private HandyTextView mHtvAge;// 年龄
	private HandyTextView mHtvSchool;//学校
	private EmoticonsTextView mEtvAccount;//嗨帮账号
	private EmoticonsTextView mEtvSign;//个性签名
	private EmoticonsTextView mEtvMail;//邮箱
	private EmoticonsTextView mEtvPhone;//电话
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mApplication = (BaseApplication) getApplication();
		user = mApplication.getUser();
		setContentView(R.layout.activity_setting_person_info);
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.setting_person_info_header);
		mHeaderLayout.myBackSettingTitle("设置");
		mHeaderLayout.mySettingMidTitle("个人资料");
		
		mIvBack = (LinearLayout)findViewById(R.id.header_iv_setting_back);
		mEditBtn = (Button)findViewById(R.id.setting_user_info_edit_btn);
		
		mAvatar = (ImageView)findViewById(R.id.setting_user_iv_avatar);
	    mHtvName = (HandyTextView)findViewById(R.id.setting_user_htv_name);
		mLayoutGender = (LinearLayout)findViewById(R.id.setting_user_layout_gender);
		mIvGender = (ImageView)findViewById(R.id.setting_user_iv_gender);
		mHtvAge = (HandyTextView)findViewById(R.id.setting_user_htv_age);
		mHtvSchool = (HandyTextView)findViewById(R.id.setting_user_htv_school);
		mEtvAccount = (EmoticonsTextView)findViewById(R.id.setting_user_info_account);
		mEtvSign = (EmoticonsTextView)findViewById(R.id.setting_user_info_own_sign);
		mEtvMail = (EmoticonsTextView)findViewById(R.id.setting_user_info_mail);
		mEtvPhone = (EmoticonsTextView)findViewById(R.id.setting_user_info_phonenum);
	}

	@Override
	protected void initEvents() {
	    
	
		mIvBack.setOnClickListener(this);
		mEditBtn.setOnClickListener(this);
		//从数据库中获取用户头像
		String path = DBManage.getPhotoPathById(user.getUserID());

		if (path == null  || path.equals("") ) {
			//mIvPhotoView.setImageResource(R.drawable.my_test_chating_image);
			CPhotoRequestMsg msg = new CPhotoRequestMsg();
			msg.setUserID(user.getUserID());
			mApplication.client.sendMessage(msg);
		} else {
			try {
				mAvatar.setImageBitmap(BitmapFactory
						.decodeStream(new FileInputStream(path)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		mHtvName.setText(user.getUsername());
		mHtvAge.setText(user.getUserAge().toString());
		
		if(user.getGender() == GENDER.MALE)
		{
			mLayoutGender.setBackgroundResource(R.drawable.bg_gender_male);
			mIvGender.setImageResource(R.drawable.ic_user_male);
		}
		else
		{
			mLayoutGender.setBackgroundResource(R.drawable.bg_gender_famal);
			mIvGender.setImageResource(R.drawable.ic_user_famale);
		}
		mEtvAccount.setText(user.getUserID().toString());
		mEtvSign.setText(user.getPs());
		mEtvMail.setText(user.getEmail());
		mEtvPhone.setText(user.getPhone());

		
	}
	
	private void init()
	{
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.header_iv_setting_back:
			this.finish();
		
			break;
		case R.id.setting_user_info_edit_btn:
			startActivity(new Intent(this,EditInfoActivity.class));
			this.finish();
		break;

		default:
			break;
		}
	}
	
	

}
