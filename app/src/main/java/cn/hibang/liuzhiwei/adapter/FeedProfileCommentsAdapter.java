package cn.hibang.liuzhiwei.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.liuzhiwei.testentity.FeedComment;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;
import cn.hibang.liuzhiwei.view.HandyTextView;
import android.content.Context;
import android.renderscript.BaseObj;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FeedProfileCommentsAdapter extends BaseAdapter{
	
	private BaseApplication mApplication;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<FeedComment> mDatas = new ArrayList<FeedComment>();
	
	public FeedProfileCommentsAdapter(BaseApplication application, Context context,
			List<FeedComment> datas)
	{
		mApplication = application;
		mContext = context;
		mInflater = LayoutInflater.from(context);
		if (datas != null) {
			mDatas = datas;
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

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.listitem_feedcomment, null);
			holder = new ViewHolder();
			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.feedcomment_item_iv_avatar);
			holder.mEtvName = (EmoticonsTextView) convertView
					.findViewById(R.id.feedcomment_item_etv_name);
			holder.mEtvContent = (EmoticonsTextView) convertView
					.findViewById(R.id.feedcomment_item_etv_content);
			holder.mHtvTime = (HandyTextView) convertView
					.findViewById(R.id.feedcomment_item_htv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FeedComment comment = (FeedComment) getItem(position);
//		holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(comment
//				.getAvatar()));
		holder.mIvAvatar.setImageResource(R.drawable.nearby_people_8);
		
		holder.mEtvName.setText(comment.getName());
		holder.mEtvContent.setText(comment.getContent());
		holder.mHtvTime.setText(comment.getTime());
		return convertView;
	}

	class ViewHolder {
		ImageView mIvAvatar;
		EmoticonsTextView mEtvName;
		EmoticonsTextView mEtvContent;
		HandyTextView mHtvTime;
	}
	public List<FeedComment> getDatas() {
		return mDatas;
	}


}
