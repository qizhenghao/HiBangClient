package cn.hibang.liuzhiwei.adapter;

import java.util.ArrayList;
import java.util.List;
import com.example.newhaibang.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.bruce.utils.MyDateUtils;
import cn.hibang.liuzhiwei.util.PhotoUtils;
import cn.hibang.liuzhiwei.view.HandyTextView;

public class RecommendAdapter extends BaseAdapter {

	String TAG = "RecommendAdapter";
	private BaseApplication mApplication;
	private LayoutInflater mInflater;
	private List<MyUserRequire> mDatas = new ArrayList<MyUserRequire>();

	public RecommendAdapter(BaseApplication application, Context context,
			List<MyUserRequire> datas) {
		this.mApplication = application;
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

		MyUserRequire temp = (MyUserRequire) getItem(position);

		holder.mIvAvatar.setImageBitmap(PhotoUtils.getPortraitById(
				temp.getUserID(), mApplication));

		holder.mHtvName.setText(temp.getUserName());
		holder.mLayoutGender.setVisibility(View.GONE);

		holder.mHtvDistance.setText(MyDateUtils.formatDateTime(temp
				.getStartTime())
				+ MyDateUtils.getHourAndMinute(temp.getStartTime()));
		holder.mHtvTime
				.setText(MyDateUtils.getHourAndMinute(temp.getEndTime()));
		holder.mHtvSign.setText(temp.getReqDetail());
		holder.msgPubTime.setText(temp.getReqItemName());
		return convertView;

	}

	public List<MyUserRequire> getDatas() {
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
