package cn.hibang.liuzhiwei.maintabs;

import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.utils.FileUtils;
import cn.hibang.huxing.clientmessage.CFriendListMsg;
import cn.hibang.huxing.msgutility.HiBangUser;
import cn.hibang.liuzhiwei.adapter.MyFriendAdapter;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.message.ChatActivity;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnCancelListener;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnRefreshListener;

@SuppressLint("ValidFragment")
public class MeHelpFriendFragment extends BaseFragment implements
		OnItemClickListener, OnRefreshListener, OnCancelListener {

	private MoMoRefreshListView mMmrlvList;
	private MyFriendAdapter mAdapter;

	// private List<TestFriend> testLists = new ArrayList<TestFriend>();

	private List<HiBangUser> mHelperList1 = new ArrayList<HiBangUser>();
	private List<HiBangUser> mHelperList2 = new ArrayList<HiBangUser>();

	private List<HiBangUser> currentList = new ArrayList<HiBangUser>();

	public MeHelpFriendFragment(BaseApplication application, Activity activity,
			Context context) {
		super(application, activity, context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_nearbypeople, container,
				false);
		System.out.println("MeHelpFragement的onCreateView调用");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	
	@Override
	public void onResume() {
		updateTradingAdapter();
		super.onResume();
		System.out.println("MeHelpFragment的onresume调用");
	}
	@Override
	protected void initViews() {

		mMmrlvList = (MoMoRefreshListView) findViewById(R.id.nearby_people_mmrlv_list);

	}

	@Override
	protected void initEvents() {
		mMmrlvList.setOnItemClickListener(this);
		mMmrlvList.setOnRefreshListener(this);
		mMmrlvList.setOnCancelListener(this);

		//
		// mHelperList1 =
		// DBManage.getFriendByCount(mHelperCount1++,FriendState.TRADING,HelpRelation.ME_HELP);
		// mHelperList2 =
		// DBManage.getFriendByCount(mHelperCount2++,FriendState.SUCCESS,HelpRelation.ME_HELP);
		// currentList = mHelperList1;

		// currentList.clear();
		// for(int i = 0;i < mHelperList1.size();i++)
		// {
		// currentList.add(mHelperList1.get(i));
		//
		// }
	}

	@Override
	protected void init() {

		mAdapter = new MyFriendAdapter(mApplication, mContext, currentList);
		mMmrlvList.setAdapter(mAdapter);
	}

	// 从FriendActivity中传过来存放两种好友状态的list用于在列表中显示
	public void setHiBangUserList(List<HiBangUser> mFriendList1,
			List<HiBangUser> mFriendList2) {
		mHelperList1.clear();
		mHelperList2.clear();
		mHelperList1.addAll(mFriendList1);
		mHelperList2.addAll(mFriendList2);
	}

	//
	//
	// 更新交易中好友的adapter
	public void updateTradingAdapter() {
		currentList.clear();
		for (int i = 0; i < mHelperList1.size(); i++) {
			currentList.add(mHelperList1.get(i));

		}
		mAdapter.notifyDataSetChanged();
	}

	// 更新交易完成好友的adapter
	public void updateSuccessAdapter() {
		currentList.clear();
		for (int i = 0; i < mHelperList2.size(); i++) {
			currentList.add(mHelperList2.get(i));

		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		int position = (int) arg3;
		// TestFriend people = mApplication.mFriendList.get(position);

		 HiBangUser friendUser = currentList.get(position);
		 Bundle bundle = new Bundle();
		 Intent intent = new Intent(mContext, ChatActivity.class);
		 bundle.putInt("friendId", friendUser.getUserID());
	     bundle.putString("friendName", friendUser.getUsername());
		 intent.putExtras(bundle);
		 startActivity(intent);

	}

	@Override
	public void onCancel() {
		clearAsyncTask();
		mMmrlvList.onRefreshComplete();
	}

	@Override
	public void onRefresh() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					FileUtils.delFolder(cn.hibang.bruce.config.Config.CACH_PATH);
					CFriendListMsg msg = new CFriendListMsg();
					msg.setUserID(mApplication.getUser().getUserID());
					mApplication.client.sendMessage(msg);
					Thread.sleep(2000);
				} catch (InterruptedException e) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				mMmrlvList.onRefreshComplete();
			}
		});
	}

	public void onManualRefresh() {
		mMmrlvList.onManualRefresh();
	}

}
