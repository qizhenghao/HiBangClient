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
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.CircleListView;
import cn.hibang.liuzhiwei.view.HeaderLayout;

public class EditSchoolActivity extends BaseActivity implements OnItemClickListener,OnClickListener{	
	 
	
	 private CircleListView lvSchoolItem;
     private ArrayList<Map<String, String>> listData;
     private SimpleAdapter adapter;
     private String[] mSchoolArr;
     
 	private HeaderLayout mHeaderLayout;
 	private LinearLayout mIvBack;


@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_setting_school_item);
	initViews();
	initEvents();
	init();
	
}

@Override
protected void initViews() {
	
	lvSchoolItem = (CircleListView) findViewById(R.id.lv_school_item);
	
	mHeaderLayout = (HeaderLayout)findViewById(R.id.school_header);
	mHeaderLayout.myBackSettingTitle("返回");
	mHeaderLayout.mySettingMidTitle("学校名称");
	
	mIvBack = (LinearLayout)findViewById(R.id.header_iv_setting_back);
}

@Override
protected void initEvents() {
	
	lvSchoolItem.setAdapter(getSimpleAdapter_1());
	lvSchoolItem.setOnItemClickListener(this);
	setListViewHeightBasedOnChildren(lvSchoolItem);
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
	
	
	Bundle bundle = this.getIntent().getExtras();
	int province = bundle.getInt("province");
	
	switch (province) {
	case 0:
	
		mSchoolArr = this.getResources().getStringArray(R.array.beijing_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 1:
		
		mSchoolArr = this.getResources().getStringArray(R.array.tianjin_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 2:
		
		mSchoolArr = this.getResources().getStringArray(R.array.hebei_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 3:
		
		mSchoolArr = this.getResources().getStringArray(R.array.shanxi1_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 4:
		
		mSchoolArr = this.getResources().getStringArray(R.array.neimenggu_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 5:
		
		mSchoolArr = this.getResources().getStringArray(R.array.liaoning_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 6:
		
		mSchoolArr = this.getResources().getStringArray(R.array.jilin_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 7:
		
		mSchoolArr = this.getResources().getStringArray(R.array.heilongjiang_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 8:
		
		mSchoolArr = this.getResources().getStringArray(R.array.shanghai_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 9:
		
		mSchoolArr = this.getResources().getStringArray(R.array.jiangsu_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 10:
		
		mSchoolArr = this.getResources().getStringArray(R.array.zhejiang_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 11:
		
		mSchoolArr = this.getResources().getStringArray(R.array.anhui_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 12:
		
		mSchoolArr = this.getResources().getStringArray(R.array.fujian_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 13:
		
		mSchoolArr = this.getResources().getStringArray(R.array.jiangxi_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 14:
		
		mSchoolArr = this.getResources().getStringArray(R.array.shandong_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 15:
		
		mSchoolArr = this.getResources().getStringArray(R.array.henan_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 16:
		
		mSchoolArr = this.getResources().getStringArray(R.array.hubei_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 17:
		
		mSchoolArr = this.getResources().getStringArray(R.array.hunan_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 18:
		
		mSchoolArr = this.getResources().getStringArray(R.array.guangdong_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 19:
		
		mSchoolArr = this.getResources().getStringArray(R.array.guangxi_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 20:
		
		mSchoolArr = this.getResources().getStringArray(R.array.hainan_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 21:
		
		mSchoolArr = this.getResources().getStringArray(R.array.chongqing_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 22:
		
		mSchoolArr = this.getResources().getStringArray(R.array.sichuan_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 23:
		
		mSchoolArr = this.getResources().getStringArray(R.array.guizhou_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 24:
		
		mSchoolArr = this.getResources().getStringArray(R.array.yunnan_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 25:
		
		mSchoolArr = this.getResources().getStringArray(R.array.xizang_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 26:
		
		mSchoolArr = this.getResources().getStringArray(R.array.shanxi3_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 27:
		
		mSchoolArr = this.getResources().getStringArray(R.array.gansu_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 28:
		
		mSchoolArr = this.getResources().getStringArray(R.array.qinghai_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 29:
		
		mSchoolArr = this.getResources().getStringArray(R.array.ningxia_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;
	case 30:
		
		mSchoolArr = this.getResources().getStringArray(R.array.xinjiang_school);
		
		for(int i = 0;i < mSchoolArr.length;i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", mSchoolArr[i]);
			listData.add(map);
		}
		
		break;				

	default:
		break;
	}
	
	

//	Map<String, String> map = new HashMap<String, String>();
//	map.put("text", "账户设置");
//	listData.add(map);
//
//	map = new HashMap<String, String>();
//	map.put("text", "个人资料");
//	listData.add(map);

	return new SimpleAdapter(EditSchoolActivity.this, listData,
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
	
	if (parent == lvSchoolItem) {

        Intent intent = new Intent(EditSchoolActivity.this,EditInfoActivity.class); 
        Bundle bundle = new Bundle();
        bundle.putString("school", mSchoolArr[position]);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
}


}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.header_iv_setting_back:
		startActivity(new Intent(EditSchoolActivity.this,EditProvinceActivity.class));
		this.finish();
		break;

	default:
		break;
	}
	
}
}
