package cn.hibang.liuzhiwei.adapter;

import java.util.ArrayList;
import java.util.List;


import com.example.newhaibang.R;


import cn.hibang.liuzhiwei.testentity.TestChild;
import cn.hibang.liuzhiwei.testentity.TestGroup;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PublishExpandableAdapter extends BaseExpandableListAdapter{
	
	private List<TestGroup> groupList;
	private List<List<TestChild>> childList;
	private Context context;
	private LayoutInflater inflater;
	
	public PublishExpandableAdapter(Context context,List<TestGroup> grouplist
			,List<List<TestChild>> childlist)
	{
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.groupList = grouplist;
		this.childList = childlist;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
			childHolder = new ChildHolder();
			convertView = inflater.inflate(R.layout.publish_content_child, null);
			
			childHolder.textName = (TextView) convertView.findViewById(R.id.name);
			childHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
			
			convertView.setTag(childHolder);
		}else{
			childHolder = (ChildHolder) convertView.getTag();
		}
		
		childHolder.textName.setText(((TestChild)getChild(groupPosition, childPosition)).getName());

		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			groupHolder = new GroupHolder();
			convertView = inflater.inflate(R.layout.publish_content_group, null);
			groupHolder.textView = (TextView) convertView
					.findViewById(R.id.group);
			groupHolder.imageView = (ImageView) convertView
					.findViewById(R.id.publish_group_image);
			groupHolder.ivGroupTitle = (ImageView) convertView
					.findViewById(R.id.publish_group_title_icon);
			groupHolder.textView.setTextSize(20);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		
		if(groupPosition == 0)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_recently_icon);
		else if(groupPosition == 1)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_sport_icon);
		else if(groupPosition == 2)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_study_icon);
		else if(groupPosition == 3)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_joy_icon);
		else if(groupPosition == 4)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_shop_icon);
		else if(groupPosition == 5)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_love_icon);
		else if(groupPosition == 6)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_life_icon);
		else if(groupPosition == 7)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_job_icon);
		else if(groupPosition == 8)
			groupHolder.ivGroupTitle.setImageResource(R.drawable.publish_other_icon);

		groupHolder.textView.setText(((TestGroup)getGroup(groupPosition)).getTitle());
		if (isExpanded)// ture is Expanded or false is not isExpanded
			groupHolder.imageView.setImageResource(R.drawable.user_setting_item_arrow_down);
		else
			groupHolder.imageView.setImageResource(R.drawable.user_setting_item_arrow);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}
	
	
	
	

	class GroupHolder {
		TextView textView;
		ImageView imageView;
		ImageView ivGroupTitle;
	}
	
	class ChildHolder {
		TextView textName;
		TextView textAge;
		TextView textAddress;
		ImageView imageView;
	}

}
