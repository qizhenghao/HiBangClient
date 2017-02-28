package cn.hibang.liuzhiwei.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.bruce.utils.MyDateUtils;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.huxing.msgutility.UserRequirement;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.adapter.RecommendAdapter;
import cn.hibang.liuzhiwei.adapter.RecommendAdapter.ViewHolder;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.message.ChatActivity;
import cn.hibang.liuzhiwei.testentity.TestFriend;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.HandyTextView;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnCancelListener;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnRefreshListener;

public class MeHelpMessageAdapter extends BaseAdapter{
	
	private BaseApplication mApplication;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<MySMeHelpMsg> mDatas = new ArrayList<MySMeHelpMsg>();
	
	public MeHelpMessageAdapter(BaseApplication application, Context context,
			List<MySMeHelpMsg> datas)
	{
		this.mApplication = application;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		
		if(this.mDatas != null)
		{
			this.mDatas = datas;
		}
	}

	public int getCount() {

		return mDatas.size();
	}

	public Object getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_message, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.message_item_iv_avatar);
			holder.mIvVip = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_vip);
			holder.mIvGroupRole = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_group_role);
			holder.mIvIndustry = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_industry);
			holder.mIvWeibo = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_weibo);
			holder.mIvTxWeibo = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_txweibo);
			holder.mIvRenRen = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_renren);
			holder.mIvDevice = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_device);
			holder.mIvRelation = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_relation);
			holder.mIvMultipic = (ImageView) convertView
					.findViewById(R.id.message_item_iv_icon_multipic);

			holder.mHtvName = (HandyTextView) convertView
					.findViewById(R.id.message_item_htv_name);
			holder.mLayoutGender = (LinearLayout) convertView
					.findViewById(R.id.message_item_layout_gender);
			holder.mIvGender = (ImageView) convertView
					.findViewById(R.id.message_item_iv_gender);
			holder.mHtvAge = (HandyTextView) convertView
					.findViewById(R.id.message_item_htv_age);
			holder.mHtvDistance = (HandyTextView) convertView
					.findViewById(R.id.message_item_htv_distance);
			holder.mHtvTime = (HandyTextView) convertView
					.findViewById(R.id.message_item_htv_time);
			holder.mHtvSign = (HandyTextView) convertView
					.findViewById(R.id.message_item_htv_sign);
			holder.msgPubTime = (HandyTextView) convertView
					.findViewById(R.id.message_item_publish_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//	//	TestFriend people = (TestFriend) getItem(position);
////		holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(people
////				.getAvatar()));
//		String path = DBManage.getPhotoPathById(mDatas.get(position)
//				.getBeHelpedUserID());
//
//		if (path == null  || path.equals("") ) {
//			holder.mIvAvatar.setImageResource(R.drawable.nearby_people_8);
//			CPhotoRequestMsg msg = new CPhotoRequestMsg();
//			msg.setUserID(mDatas.get(position).getHelpUserID());
//			mApplication.client.sendMessage(msg);
//		} else {
//			try {
//				holder.mIvAvatar.setImageBitmap(BitmapFactory
//						.decodeStream(new FileInputStream(path)));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		MySMeHelpMsg currentMsg = (MySMeHelpMsg)getItem(position);
//		
////		holder.mIvAvatar.setImageResource(R.drawable.nearby_people_8);
//		
//		
//		holder.mHtvName.setText(currentMsg.getBeHelpedName());
//		holder.mLayoutGender.setVisibility(View.GONE);
//		
//		holder.mHtvDistance.setText(currentMsg.getStartTime());
//		holder.mHtvTime.setText(currentMsg.getEndTime());
//		holder.mHtvSign.setText(currentMsg.getReqItem());
////		if (people.getIsVip() != 0) {
////			holder.mIvVip.setVisibility(View.VISIBLE);
////		} else {
////			holder.mIvVip.setVisibility(View.GONE);
////		}
////		if (people.getIsGroupRole() != 0) {
////			holder.mIvGroupRole.setVisibility(View.VISIBLE);
////			if (people.getIsGroupRole() == 1) {
////				holder.mIvGroupRole
////						.setImageResource(R.drawable.ic_userinfo_groupowner);
////			}
////		} else {
////			holder.mIvIndustry.setVisibility(View.GONE);
////		}
////		if (!android.text.TextUtils.isEmpty(people.getIndustry())) {
////			holder.mIvIndustry.setVisibility(View.VISIBLE);
////			holder.mIvIndustry.setImageBitmap(PhotoUtils.getIndustry(mContext,
////					people.getIndustry()));
////		} else {
////			holder.mIvIndustry.setVisibility(View.GONE);
////		}
////		if (people.getIsbindWeibo() != 0) {
////			holder.mIvWeibo.setVisibility(View.VISIBLE);
////			if (people.getIsbindWeibo() == 1) {
////				holder.mIvWeibo.setImageResource(R.drawable.ic_userinfo_weibov);
////			}
////		} else {
////			holder.mIvWeibo.setVisibility(View.GONE);
////		}
////		if (people.getIsbindTxWeibo() != 0) {
////			holder.mIvTxWeibo.setVisibility(View.VISIBLE);
////			if (people.getIsbindTxWeibo() == 1) {
////				holder.mIvTxWeibo
////						.setImageResource(R.drawable.ic_userinfo_tweibov);
////			}
////		} else {
////			holder.mIvTxWeibo.setVisibility(View.GONE);
////		}
////
////		if (people.getIsbindRenRen() != 0) {
////			holder.mIvRenRen.setVisibility(View.VISIBLE);
////		} else {
////			holder.mIvRenRen.setVisibility(View.GONE);
////		}
////		if (people.getDevice() != 0) {
////			holder.mIvDevice.setVisibility(View.VISIBLE);
////			if (people.getDevice() == 1) {
////				holder.mIvDevice
////						.setImageResource(R.drawable.ic_userinfo_android);
////			}
////			if (people.getDevice() == 2) {
////				holder.mIvDevice.setImageResource(R.drawable.ic_userinfo_apple);
////			}
////		} else {
////			holder.mIvDevice.setVisibility(View.GONE);
////		}
////		if (people.getIsRelation() != 0) {
////			holder.mIvRelation.setVisibility(View.VISIBLE);
////		} else {
////			holder.mIvRelation.setVisibility(View.GONE);
////		}
////		if (people.getIsMultipic() != 0) {
////			holder.mIvMultipic.setVisibility(View.VISIBLE);
////		} else {
////			holder.mIvMultipic.setVisibility(View.GONE);
////		}
//		return convertView;
//		
	
		MySMeHelpMsg temp = (MySMeHelpMsg) getItem(position);

		holder.mIvAvatar.setImageBitmap(PhotoUtils.getPortraitById(
				temp.getHelpUserID(), mApplication));

		holder.mHtvName.setText(temp.getHelpName());
		holder.mLayoutGender.setVisibility(View.GONE);

		holder.mHtvDistance.setText(MyDateUtils.formatDateTime(temp
				.getStartTime())
				+ MyDateUtils.getHourAndMinute(temp.getStartTime()));
		holder.mHtvTime
				.setText(MyDateUtils.getHourAndMinute(temp.getEndTime()));
		holder.mHtvSign.setText(temp.getReqDetail());
		holder.msgPubTime.setText(temp.getReqItem());
		return convertView;
	}
	
	public List<MySMeHelpMsg> getDatas()
	{
		return mDatas;
	}
	
	
	class ViewHolder {

		ImageView mIvAvatar;
		ImageView mIvVip;
		ImageView mIvGroupRole;
		ImageView mIvIndustry;
		ImageView mIvWeibo;
		ImageView mIvTxWeibo;
		ImageView mIvRenRen;
		ImageView mIvDevice;
		ImageView mIvRelation;
		ImageView mIvMultipic;
		HandyTextView mHtvName;
		LinearLayout mLayoutGender;
		ImageView mIvGender;
		HandyTextView mHtvAge;
		HandyTextView mHtvDistance;
		HandyTextView mHtvTime;
		HandyTextView mHtvSign;
		
		HandyTextView msgPubTime;
	}
	

}
