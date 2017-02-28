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
import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.DataHiBang;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.adapter.MeHelpMessageAdapter;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.message.ChatActivity;
import cn.hibang.liuzhiwei.testentity.TestFriend;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnCancelListener;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnRefreshListener;

@SuppressLint("ValidFragment")
public class MeHelpMessageFragment extends BaseFragment implements
		OnItemClickListener, OnRefreshListener, OnCancelListener {

	private MoMoRefreshListView mMmrlvList;
	private MeHelpMessageAdapter mAdapter;
	

	private List<MySMeHelpMsg> meHelpMsgList = new ArrayList<MySMeHelpMsg>();

	public MeHelpMessageFragment(BaseApplication application, Activity activity,
			Context context) {
		super(application, activity, context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_nearbypeople, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
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

	}

	@Override
	protected void init() {

		meHelpMsgList.clear();
		synchronized (String.class) {
		for(int i=DataHiBang.sMeHelpMsgList.size();i>0;i--) {
			MySMeHelpMsg myMsg = new MySMeHelpMsg(DataHiBang.sMeHelpMsgList.get(i-1));
			meHelpMsgList.add(myMsg);
		}
		}
//		meHelpMsgList = DBManage.getMeHelpMsgByCount(1);
		mAdapter = new MeHelpMessageAdapter(mApplication, mContext,
				meHelpMsgList);
		mMmrlvList.setAdapter(mAdapter);
	}

	public void setMeHelpMsg(SMeHelpMsg msg) {
		mAdapter = new MeHelpMessageAdapter(mApplication, mContext,
				meHelpMsgList);
		meHelpMsgList.clear();
		synchronized (String.class) {
		for(int i=DataHiBang.sMeHelpMsgList.size();i>0;i--) {
			MySMeHelpMsg myMsg = new MySMeHelpMsg(DataHiBang.sMeHelpMsgList.get(i-1));
			meHelpMsgList.add(myMsg);
		}
		}
//		MySMeHelpMsg currentMsg = new MySMeHelpMsg();
//		currentMsg.setBeHelpedName(msg.getBeHelpedName());
//		currentMsg.setBeHelpedUserID(msg.getBeHelpedUserID());
//		currentMsg.setEndTime(msg.getEndTime());
//		currentMsg.setHelpName(msg.getHelpName());
//		currentMsg.setHelpUserID(msg.getBeHelpedUserID());
//		currentMsg.setMsgTime(msg.getMsgTime());
//		currentMsg.setNoRead(true);
//		currentMsg.setReqDetail(msg.getReqDetail());
//		currentMsg.setReqItem(msg.getReqItem());
//		currentMsg.setReqItemID(msg.getReqItemID());
//		currentMsg.setStartTime(msg.getStartTime());
//
//		meHelpMsgList.add(currentMsg);

		mAdapter.notifyDataSetChanged();
	}

	public void refrsh() {
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		int position = (int) arg3;
		
		Intent intent = new Intent(mContext,InformationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("TAG", Config.TAG_MeHelpMessage);
		bundle.putSerializable("meHelpMessage", meHelpMsgList.get(position));
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
//					init();
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
