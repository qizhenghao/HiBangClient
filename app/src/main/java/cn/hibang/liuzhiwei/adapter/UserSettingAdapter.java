package cn.hibang.liuzhiwei.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import cn.hibang.liuzhiwei.adapter.MessageAdapter.ViewHolder;
import cn.hibang.liuzhiwei.testentity.SettingItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSettingAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	List<SettingItem> settingList = new ArrayList<SettingItem>();
	
	public UserSettingAdapter(Context context, List<SettingItem> datas)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		
		if(this.settingList != null)
		{
			this.settingList = datas;
		}
	}
	
	
	@Override
	public int getCount() {
		
		return settingList.size();
	}

	@Override
	public Object getItem(int position) {
	
		return settingList.get(position);
	}

	@Override
	public long getItemId(int position) {
       return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
     
		ViewHolder holder = null;
		if (convertView == null) 
		{
			convertView = mInflater.inflate(R.layout.listitem_usersetting, null);
			holder = new ViewHolder();
			
			holder.settingPhoto = (ImageView)convertView.findViewById(R.id.setting_item_photo);
			holder.settingItem = (TextView)convertView.findViewById(R.id.setting_item_text);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		SettingItem item = (SettingItem)getItem(position);
		
		holder.settingItem.setText(item.getSettingName());
		
		return convertView;
	}
	
	
	class ViewHolder
	{
		ImageView settingPhoto;
		TextView settingItem;
	}

}
