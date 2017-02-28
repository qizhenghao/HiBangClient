package cn.hibang.liuzhiwei.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.liuzhiwei.message.MessageItem;
import cn.hibang.liuzhiwei.message.MessageItem.onClickAvatarListener;
import cn.hibang.liuzhiwei.testentity.MyMessage;
import cn.hibang.liuzhiwei.view.HandyTextView;

public class ChatAdapter extends BaseAdapter implements onClickAvatarListener{

	private  BaseApplication mApplication;
	private  Context mContext;
	private LayoutInflater mInflater;
	private List<MyMessage> mDatas = new ArrayList<MyMessage>();
	
	public ChatAdapter(BaseApplication application, Context context,
			List<MyMessage> datas)
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyMessage msg = (MyMessage) getItem(position);
		MessageItem messageItem = MessageItem.getInstance(msg, mContext,mApplication);
		messageItem.fillContent();
		View view = messageItem.getRootView();
		return view;
	}
	
	public List<MyMessage> getDatas()
	{
		return mDatas;
	}
	
	public void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(mContext).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	@Override
	public void onClick() {
		
		
	}

}
