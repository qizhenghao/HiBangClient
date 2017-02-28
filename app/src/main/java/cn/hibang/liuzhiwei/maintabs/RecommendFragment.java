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
import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.huxing.msgutility.UserRequirement;
import cn.hibang.liuzhiwei.adapter.RecommendAdapter;
import cn.hibang.liuzhiwei.android.BaseFragment;
import cn.hibang.liuzhiwei.message.ChatActivity;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnCancelListener;
import cn.hibang.liuzhiwei.view.MoMoRefreshListView.OnRefreshListener;

@SuppressLint("ValidFragment")
public class RecommendFragment extends BaseFragment implements
		OnItemClickListener, OnRefreshListener, OnCancelListener {

	private MoMoRefreshListView mMmrlvList;
	private RecommendAdapter mAdapter;
	
	private List<MyUserRequire> userReqList = new ArrayList<MyUserRequire>(); 

	public RecommendFragment(BaseApplication application, Activity activity,
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
		userReqList.clear();
		synchronized (String.class) {
			for(int i=DataHiBang.sRecommMsgList.size();i>0;i--) {
				MyUserRequire currentReq = new MyUserRequire();
				currentReq.setNoRead(true);
				currentReq.setHelpme_id(DataHiBang.sRecommMsgList.get(i-1).getHelpme_id());
				currentReq.setStartTime(DataHiBang.sRecommMsgList.get(i-1).getStartTime());
				currentReq.setEndTime(DataHiBang.sRecommMsgList.get(i-1).getEndTime());
				currentReq.setReqDetail(DataHiBang.sRecommMsgList.get(i-1).getReqDetail());
				currentReq.setReqItemID(DataHiBang.sRecommMsgList.get(i-1).getReqItemID());
				currentReq.setReqItemName(DataHiBang.sRecommMsgList.get(i-1).getReqItemName());
				currentReq.setUserID(DataHiBang.sRecommMsgList.get(i-1).getUserID());
				currentReq.setUserName(DataHiBang.sRecommMsgList.get(i-1).getUserName());
				userReqList.add(currentReq);
			}
		}
		mAdapter = new RecommendAdapter(mApplication, mContext, userReqList);
		mMmrlvList.setAdapter(mAdapter);
	}

	
	public void setUserReqList(List<UserRequirement> list)
	{
		userReqList.clear();
		synchronized (String.class) {
			for(int i=DataHiBang.sRecommMsgList.size();i>0;i--) {
				MyUserRequire currentReq = new MyUserRequire();
				currentReq.setNoRead(true);
				currentReq.setHelpme_id(DataHiBang.sRecommMsgList.get(i-1).getHelpme_id());
				currentReq.setStartTime(DataHiBang.sRecommMsgList.get(i-1).getStartTime());
				currentReq.setEndTime(DataHiBang.sRecommMsgList.get(i-1).getEndTime());
				currentReq.setReqDetail(DataHiBang.sRecommMsgList.get(i-1).getReqDetail());
				currentReq.setReqItemID(DataHiBang.sRecommMsgList.get(i-1).getReqItemID());
				currentReq.setReqItemName(DataHiBang.sRecommMsgList.get(i-1).getReqItemName());
				currentReq.setUserID(DataHiBang.sRecommMsgList.get(i-1).getUserID());
				currentReq.setUserName(DataHiBang.sRecommMsgList.get(i-1).getUserName());
				userReqList.add(currentReq);
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		MyUserRequire require = userReqList.get((int)arg3);
		Intent intent = new Intent(mContext, InformationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("TAG", Config.TAG_RecomMessage);
//		bundle.putSerializable("recomMessage", userReqList.get(arg2));
		bundle.putInt("userId", require.getUserID());
		bundle.putInt("ReqItemID",  require.getReqItemID());
		bundle.putString("EndTime", require.getEndTime());
		bundle.putString("StartTime", require.getStartTime());
		bundle.putString("ReqDetail", require.getReqDetail());
		bundle.putString("ReqItemName", require.getReqItemName());
		bundle.putInt("Helpme_id", require.getHelpme_id());
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
