package cn.hibang.liuzhiwei.maintabs;

import java.util.ArrayList;
import java.util.List;



import com.example.newhaibang.R;


import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.domain.MyPubMeHelp;
import cn.hibang.huxing.clientmessage.CPubMeHelpMsg;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.adapter.PublishExpandableAdapter;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.android.BasePopupWindow.onSubmitClickListener;
import cn.hibang.liuzhiwei.popupwindow.PublishPopupWindow;
import cn.hibang.liuzhiwei.testentity.TestChild;
import cn.hibang.liuzhiwei.testentity.TestGroup;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow.OnDismissListener;

@SuppressLint("ValidFragment")
public class HelperPublishFragment extends BaseFragment implements ExpandableListView.OnChildClickListener,
ExpandableListView.OnGroupClickListener
{
	private ExpandableListView expandableListView;
	private List<TestGroup> groupList;
	private List<List<TestChild>> childList;
	
	private PublishExpandableAdapter mAdapter;
	

	private BaseApplication application = null;
	private Activity activity = null;
	private Context context = null;
	private PublishPopupWindow mPopupWindow;
	private boolean flag = false;
	
	public HelperPublishFragment(BaseApplication application, Activity activity,
			Context context) {
		super(application, activity, context);
		this.application = application;
		this.activity = activity;
		this.context = context;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_publish, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
	
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		//System.out.println("群编号为:" + groupPosition + "子编号为:" + childPosition);

		int count = 0;
		for (int i = 1; i < groupPosition; i++) {
			count += childList.get(i).size();
		}
		count = count + childPosition + 1;
		// CPubHelpMeMsg msg = new CPubHelpMeMsg();
		// msg.setReqItemID(count);
		// msg.setUserID(application.getUser().getUserID());
		// msg.setStartTime("2014-03-16 12:30:00");
		// msg.setEndTime("2014-03-17 12:30:00");
		// msg.setReqDetail("最好是粉色的哦！");
		// application.client.sendMessage(msg);
		// MyPubHelpMe myMsg = new
		// MyPubHelpMe(msg,childList.get(groupPosition).get(childPosition).getName());
		// DBManage.addCPubHelpMe(myMsg);

		mPopupWindow = new PublishPopupWindow(mActivity, mApplication, count,
				childList.get(groupPosition).get(childPosition).getName(),flag);
		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {

			@Override
			public void onClick() {
				

			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				// mHeaderSpinner.initSpinnerState(false);
			}
		});

		mPopupWindow
				.showViewTopCenter(findViewById(R.id.publish_fragment_root));

		return false;
	}

	@Override
	protected void initViews() {
	
		expandableListView = (ExpandableListView) findViewById(R.id.expandablelist);
		
	}

	@Override
	protected void initEvents() {
		
		initData();
		
		mAdapter = new PublishExpandableAdapter(mContext, groupList, childList);
		expandableListView.setAdapter(mAdapter);
		
		expandableListView.setOnChildClickListener(this);
		expandableListView.setOnGroupClickListener(this);
	}

	@Override
	protected void init() {

		
	}
	
	private void initData()
	{

		groupList = new ArrayList<TestGroup>();
		
		groupList.add(new TestGroup("最近发布"));
		groupList.add(new TestGroup("运动"));
		groupList.add(new TestGroup("学习"));
		groupList.add(new TestGroup("娱乐"));
		groupList.add(new TestGroup("购物"));
		groupList.add(new TestGroup("情感"));
		groupList.add(new TestGroup("生活"));
		groupList.add(new TestGroup("工作"));
		groupList.add(new TestGroup("其他"));
		

		
		
		childList = new ArrayList<List<TestChild>>();
		for (int i = 0; i < groupList.size(); i++) {
			ArrayList<TestChild> childTemp;
			if (i == 0) {
				childTemp = new ArrayList<TestChild>();
				List<MyPubMeHelp> meHelpList  = DBManage.getAllCPubMeHelp();
				for(int n=meHelpList.size();n>0;n--) {
					childTemp.add(new TestChild(meHelpList.get(n-1).getReqItemName(), 0, ""));
				}
				
			} else if (i == 1) {
				childTemp = new ArrayList<TestChild>();
				childTemp.add(new TestChild("跑步", 0, ""));
				childTemp.add(new TestChild("乒乓球", 0, ""));
				childTemp.add(new TestChild("排球", 0, ""));
				childTemp.add(new TestChild("羽毛球", 0, ""));
				childTemp.add(new TestChild("网球", 0, ""));
				childTemp.add(new TestChild("篮球", 0, ""));
				childTemp.add(new TestChild("足球", 0, ""));
				childTemp.add(new TestChild("游泳", 0, ""));
				
			} else if (i == 2){
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("自习", 0, ""));
				childTemp.add(new TestChild("图书馆占座", 0, ""));
				childTemp.add(new TestChild("自习室占座", 0, ""));
				childTemp.add(new TestChild("借(入)书", 0, ""));
				childTemp.add(new TestChild("留学", 0, ""));
				childTemp.add(new TestChild("一起考托福（学英语）托儿 烤鸭", 0, ""));
				childTemp.add(new TestChild("考研", 0, ""));
				childTemp.add(new TestChild("好书推荐", 0, ""));

			}
			else if (i == 3){
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("演唱会 电影", 0, ""));
				childTemp.add(new TestChild("游戏代练", 0, ""));
				childTemp.add(new TestChild("酒吧", 0, ""));
				childTemp.add(new TestChild("吐槽", 0, ""));
				childTemp.add(new TestChild("打麻将", 0, ""));

			}
			else if (i == 4){
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("外卖", 0, ""));
				childTemp.add(new TestChild("抢手机", 0, ""));
				childTemp.add(new TestChild("团购", 0, ""));

			}
			else if (i == 5){
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("陪逛校园", 0, ""));
				childTemp.add(new TestChild("心理辅导", 0, ""));
				childTemp.add(new TestChild("表白", 0, ""));

			}
			else if (i == 6){
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("洗衣服", 0, ""));
				childTemp.add(new TestChild("洗袜子", 0, ""));
				childTemp.add(new TestChild("懒猪起床", 0, ""));
				childTemp.add(new TestChild("结拜旅游，穷途苦旅", 0, ""));
				childTemp.add(new TestChild("物品交换", 0, ""));
				childTemp.add(new TestChild("修电脑", 0, ""));
				childTemp.add(new TestChild("找老乡", 0, ""));
				childTemp.add(new TestChild("取快递", 0, ""));

			}
			else
			{
				childTemp = new ArrayList<TestChild>();
				
				childTemp.add(new TestChild("找兼职", 0, ""));
				childTemp.add(new TestChild("找工作", 0, ""));
				
			}
			childList.add(childTemp);
		}
		
	
	
		
	
	}

}
