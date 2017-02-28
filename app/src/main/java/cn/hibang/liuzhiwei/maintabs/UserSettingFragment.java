package cn.hibang.liuzhiwei.maintabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.newhaibang.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.liaohongxian.activity.AboutActivity;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.setting.PersonInfoActivity;
import cn.hibang.liuzhiwei.view.CircleListView;

@SuppressLint("ValidFragment")
public class UserSettingFragment extends BaseFragment implements
OnItemClickListener{
	
//	UserSettingAdapter mAdapter;
//	PullDownListview mPdlvList;
//	List<SettingItem> mList = new ArrayList<SettingItem>();
	
	private CircleListView listView_1, listView_2, listView_3;
	private ArrayList<Map<String, String>> listData, listData2, listData3;
	
	public UserSettingFragment(BaseApplication application, Activity activity,
			Context context) {
		super(application, activity, context);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_real_setting, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void initViews() {
	
		listView_1 = (CircleListView) findViewById(R.id.mylistview_1);
		listView_2 = (CircleListView) findViewById(R.id.mylistview_2);
		listView_3 = (CircleListView) findViewById(R.id.mylistview_3);
	
	}

	@Override
	protected void initEvents() {
		listView_1.setAdapter(getSimpleAdapter_1());
		listView_2.setAdapter(getSimpleAdapter_2());
		listView_3.setAdapter(getSimpleAdapter_3());

		listView_1.setOnItemClickListener(this);
		listView_2.setOnItemClickListener(this);
		listView_3.setOnItemClickListener(this);

		setListViewHeightBasedOnChildren(listView_1);
		setListViewHeightBasedOnChildren(listView_2);
		setListViewHeightBasedOnChildren(listView_3);
	
	}

	@Override
	protected void init() {
	
		
	}

	/**
	 * 设置第一列数据
	 */
	private SimpleAdapter getSimpleAdapter_1() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "账户设置");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "个人资料");
		listData.add(map);

		return new SimpleAdapter(mContext, listData,
				R.layout.setting_list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/**
	 * 设置第二列数据
	 */
	private SimpleAdapter getSimpleAdapter_2() {
		listData2 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "屏蔽发现");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "会员介绍");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "意见反馈");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "小嗨帮助");
		listData2.add(map);


		return new SimpleAdapter(mContext, listData2,
				R.layout.setting_list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/**
	 * 设置第三列数据
	 */
	private SimpleAdapter getSimpleAdapter_3() {
		listData3 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "注销登录");
		listData3.add(map);



		return new SimpleAdapter(mContext, listData3,
				R.layout.setting_list_item1, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/***
	 * 动态设置listview的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if (parent == listView_1) {
			//Map<String, String> map = listData.get(position);
			//Toast.makeText(mContext, map.get("text"), 1).show();
			if(position == 1)
			{
				startActivity(new Intent(mActivity,PersonInfoActivity.class));
			}
		} else if (parent == listView_2) {
			Map<String, String> map = listData2.get(position);
			if(position == 0) {
				//屏蔽发现
			} else if(position == 1) {
				//会员介绍
			} else if(position == 2){
				//意见反馈
			} else {
				//小嗨帮助
				startActivity(AboutActivity.class);
			}
			Toast.makeText(mContext, map.get("text"), 1).show();
		} else if (parent == listView_3){
			mApplication.getHiBang().clearAccessToken();
			mApplication.clearActivity();
		}

	}

}

	
	

