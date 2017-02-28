package cn.hibang.liuzhiwei.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.newhaibang.R;

import android.R.array;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.HandyTextView;

public class MyFriendAdapter extends BaseAdapter {

	private BaseApplication mApplication;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<HiBangUser> mDatas = new ArrayList<HiBangUser>();
	private Random random = new Random(1000);
	private int vipNum;
	private int starNum;


	public MyFriendAdapter(BaseApplication application, Context context,
			List<HiBangUser> datas) {
		this.mApplication = application;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);

		if (this.mDatas != null) {
			this.mDatas = datas;
		}
	}

	@Override
	public int getCount() {

		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_user, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.user_item_iv_avatar);
			holder.mIvVip1 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_vip1);
			holder.mIvVip2 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_vip2);
			holder.mIvVip3 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_vip3);
			holder.mIvStar1 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_star1);
			holder.mIvStar2 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_star2);
			holder.mIvStar3 = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_star3);
			holder.mIvVipTitle = (ImageView) convertView
					.findViewById(R.id.user_item_iv_vip_title);
			
	
			holder.mHtvName = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_name);
			holder.mLayoutGender = (LinearLayout) convertView
					.findViewById(R.id.user_item_layout_gender);
			holder.mIvGender = (ImageView) convertView
					.findViewById(R.id.user_item_iv_gender);
			holder.mHtvAge = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_age);
			holder.mHtvDistance = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_distance);
			holder.mHtvTime = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_time);
			holder.mHtvSign = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_sign);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HiBangUser currentUser = (HiBangUser) getItem(position);

		holder.mIvAvatar.setImageBitmap(PhotoUtils.getPortraitById(mDatas.get(position).getUserID(), mApplication));

		holder.mHtvName.setText(currentUser.getUsername());
		if (currentUser.getGender() == GENDER.FEMALE) {
			holder.mLayoutGender
					.setBackgroundResource(R.drawable.bg_gender_famal);
			holder.mIvGender.setImageResource(R.drawable.ic_user_famale);
		} else {
			holder.mLayoutGender
					.setBackgroundResource(R.drawable.bg_gender_male);
			holder.mIvGender.setImageResource(R.drawable.ic_user_male);
		}

		holder.mHtvAge.setText(currentUser.getUserAge().toString());
		holder.mHtvDistance.setText(currentUser.getSchool());
		holder.mHtvTime.setText(getRandomDistance()+"km");
		holder.mHtvSign.setText(currentUser.getPs());
		
		if(currentUser.isVipUser())
		{
			holder.mIvVipTitle.setVisibility(View.VISIBLE);
			holder.mHtvName.setTextColor(android.graphics.Color.RED);
		}
		
		 getLevelByNum();
		 
		 if(vipNum == 1)
			 holder.mIvVip1.setVisibility(View.VISIBLE);
		 else if(vipNum == 2)
		 {
			 holder.mIvVip1.setVisibility(View.VISIBLE);
			 holder.mIvVip2.setVisibility(View.VISIBLE);
		 }
		 else if(vipNum == 3)
		 {
			 holder.mIvVip1.setVisibility(View.VISIBLE);
			 holder.mIvVip2.setVisibility(View.VISIBLE);
			 holder.mIvVip3.setVisibility(View.VISIBLE);
		 }
		 
		 if(starNum == 1)
			 holder.mIvStar1.setVisibility(View.VISIBLE);
		 else if(starNum == 2)
		 {
			 holder.mIvStar1.setVisibility(View.VISIBLE);
			 holder.mIvStar2.setVisibility(View.VISIBLE);
		 }
		 else if(starNum ==3)
		 {
			 holder.mIvStar1.setVisibility(View.VISIBLE);
			 holder.mIvStar2.setVisibility(View.VISIBLE);
			 holder.mIvStar3.setVisibility(View.VISIBLE);
		 }
		 
		
		
		return convertView;

	}

	public List<HiBangUser> getDatas() {
		return mDatas;
	}

	
	public float getRandomDistance()
	{
		int m = (int) (Math.random()*50);
		float n = (float)m/10;

		return n;
		
	}
	

	
	public void getLevelByNum()
	{
		
		int temp  = (int) (Math.random()*15);	
			
			vipNum = temp/4;
			starNum = temp%3;
	}
	
	class ViewHolder {

		ImageView mIvAvatar;
		ImageView mIvVip1;
		ImageView mIvVip2;
		ImageView mIvVip3;
		ImageView mIvStar1;
		ImageView mIvStar2;
		ImageView mIvStar3;
		ImageView mIvVipTitle;
		
	
		HandyTextView mHtvName;
		LinearLayout mLayoutGender;
		ImageView mIvGender;
		HandyTextView mHtvAge;
		HandyTextView mHtvDistance;
		HandyTextView mHtvTime;
		HandyTextView mHtvSign;
	}

}
