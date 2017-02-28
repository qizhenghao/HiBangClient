package cn.hibang.liuzhiwei.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.newhaibang.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.CircleListView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

public class EditProvinceActivity extends BaseActivity implements OnItemClickListener,OnClickListener{
	
	private CircleListView lvProvinceItem;
	private ArrayList<Map<String, String>> listData;
	private SimpleAdapter adapter;
	
	private HeaderLayout mHeaderLayout;
	private LinearLayout mIvBack;
	private String[] mProvinceArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_province);
		initViews();
		initEvents();
		init();
		
	}

	@Override
	protected void initViews() {
		
		lvProvinceItem = (CircleListView) findViewById(R.id.lv_province_item);
		
		mHeaderLayout = (HeaderLayout)findViewById(R.id.province_header);
		mHeaderLayout.myBackSettingTitle("返回");
		mHeaderLayout.mySettingMidTitle("学校省份");
		
		mIvBack = (LinearLayout)findViewById(R.id.header_iv_setting_back);
	}

	@Override
	protected void initEvents() {
		
		lvProvinceItem.setAdapter(getSimpleAdapter_1());
		lvProvinceItem.setOnItemClickListener(this);
		setListViewHeightBasedOnChildren(lvProvinceItem);
		mIvBack.setOnClickListener(this);

	}
	
	private void init()
	{
		
	}
	
	/**
	 * 设置第一列数据
	 */
	private SimpleAdapter getSimpleAdapter_1() {
		listData = new ArrayList<Map<String, String>>();
		
		mProvinceArr = this.getResources().getStringArray(R.array.province);
		
		for(int i = 0;i < mProvinceArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mProvinceArr[i]);
			listData.add(map);
		}

//		Map<String, String> map = new HashMap<String, String>();
//		map.put("text", "账户设置");
//		listData.add(map);
//
//		map = new HashMap<String, String>();
//		map.put("text", "个人资料");
//		listData.add(map);

		return new SimpleAdapter(EditProvinceActivity.this, listData,
				R.layout.province_list_item, new String[] { "text" },
				new int[] { R.id.tv_province_list_item });

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
		
		if (parent == lvProvinceItem) {
			//Map<String, String> map = listData.get(position);
			//Toast.makeText(mContext, map.get("text"), 1).show();

				Intent intent = new Intent(EditProvinceActivity.this,EditSchoolActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("province", position);
				intent.putExtras(bundle);
				startActivity(intent);
				this.finish();



	}


}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.header_iv_setting_back:
			startActivity(new Intent(EditProvinceActivity.this,EditInfoActivity.class));
			this.finish();
			break;

		default:
			break;
		}
		
	}
}
