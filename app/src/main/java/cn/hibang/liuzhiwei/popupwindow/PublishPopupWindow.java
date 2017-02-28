package cn.hibang.liuzhiwei.popupwindow;

import java.util.Calendar;

import com.example.newhaibang.R;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.bruce.domain.MyPubHelpMe;
import cn.hibang.bruce.domain.MyPubMeHelp;
import cn.hibang.huxing.clientmessage.CPubHelpMeMsg;
import cn.hibang.huxing.clientmessage.CPubMeHelpMsg;
import cn.hibang.liaohongxian.dao.DBManage;
import cn.hibang.liuzhiwei.adapter.SimpleListDialogAdapter;
import cn.hibang.liuzhiwei.android.BasePopupWindow;
import cn.hibang.liuzhiwei.dialog.SimpleListDialog;
import cn.hibang.liuzhiwei.dialog.SimpleListDialog.onSimpleListItemClickListener;
import cn.hibang.liuzhiwei.testentity.TestChild;
import cn.hibang.liuzhiwei.view.HandyTextView;
import android.R.bool;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class PublishPopupWindow extends BasePopupWindow implements  onSimpleListItemClickListener{
	
	private LinearLayout mLayoutRoot;
	private LinearLayout details_linearLayout;
	private HandyTextView mPubHelptv;
	private HandyTextView mPubStartHtv;
	private HandyTextView mPubStartMtv;
	private HandyTextView mPubEndHtv;
	private HandyTextView mPubEndMtv;
	private EditText edtTxt_details;
	private Button mBtnSubmit;
	private Button mBtnCancel;
	private Context mContext;
	private RadioGroup mRgPubDay;
	
	private String[] mHours;
	private String[] mMinutes;
	private int flag = 0;
	private BaseApplication application = null;
	private int count = 0;
	private String itemName = "";
	private int year;
	private int month;
	private int day;
	private boolean relation;
	
	
	private SimpleListDialog mSimpleListDialog;
	
	public PublishPopupWindow(Context context)
	{
		super(LayoutInflater.from(context).inflate(
				R.layout.include_dialog_publish_check, null),
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.Popup_Animation_PushDownUp);
		
		this.mContext = context;
	}
	
	public PublishPopupWindow(Context context, BaseApplication application, int count, String itemName,boolean flag) {
		
		super(LayoutInflater.from(context).inflate(
				R.layout.include_dialog_publish_check, null),
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.Popup_Animation_PushDownUp);
		
		this.mContext = context;
		this.application = application;
		this.count = count;
		this.itemName = itemName;
		this.relation = flag;
		mPubHelptv.setText(itemName);
	}
	

	@Override
	public void initViews() {
		mLayoutRoot = (LinearLayout) findViewById(R.id.dialog_publish_layout_root);
		mPubHelptv = (HandyTextView)findViewById(R.id.publish_help_textview);
		mRgPubDay = (RadioGroup)findViewById(R.id.dialog_publish_rg_day);
		mPubStartHtv = (HandyTextView)findViewById(R.id.publish_start_time_hour);
		mPubStartMtv = (HandyTextView)findViewById(R.id.publish_start_time_minute);
		mPubEndHtv = (HandyTextView)findViewById(R.id.publish_end_time_hour);
		mPubEndMtv = (HandyTextView)findViewById(R.id.publish_end_time_minute);
		edtTxt_details = (EditText)findViewById(R.id.pubish_ps_textview);
		mBtnSubmit = (Button) findViewById(R.id.dialog_publish_btn_submit);
		mBtnCancel = (Button) findViewById(R.id.dialog_publish_btn_cancel);
		
		details_linearLayout = (LinearLayout) findViewById(R.id.pop_pub_details_linearLayout);
//		if(!relation){
//			details_linearLayout.setVisibility(View.GONE);
//		} else {
//			details_linearLayout.setVisibility(View.VISIBLE);
//		}
	}

	@Override
	public void initEvents() {
		
		
		mPubStartHtv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHours = mContext.getResources().getStringArray(
						R.array.time_hour);

				mSimpleListDialog = new SimpleListDialog(mContext);
				mSimpleListDialog.setTitle("选择时间(时)");
				mSimpleListDialog.setTitleLineVisibility(View.GONE);
				mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext,
						mHours));
				mSimpleListDialog.setOnSimpleListItemClickListener(PublishPopupWindow.this);
				mSimpleListDialog.show();
				flag = 0;
				
				
			}
		}		);
		
		mPubStartMtv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mMinutes = mContext.getResources().getStringArray(
						R.array.time_minute);
				
				mSimpleListDialog = new SimpleListDialog(mContext);
				mSimpleListDialog.setTitle("选择时间(分)");
				mSimpleListDialog.setTitleLineVisibility(View.GONE);
				mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext,
						mMinutes));
				mSimpleListDialog.setOnSimpleListItemClickListener(PublishPopupWindow.this);
				mSimpleListDialog.show();
			
				flag = 1;
				
			}
		}
				);
		mPubEndHtv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHours = mContext.getResources().getStringArray(
						R.array.time_hour);
				
				mSimpleListDialog = new SimpleListDialog(mContext);
				mSimpleListDialog.setTitle("选择时间(时)");
				mSimpleListDialog.setTitleLineVisibility(View.GONE);
				mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext,
						mHours));
				mSimpleListDialog.setOnSimpleListItemClickListener(PublishPopupWindow.this);
				mSimpleListDialog.show();
				
				flag = 2;
				
			}
		}
				);
		mPubEndMtv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				mMinutes = mContext.getResources().getStringArray(
						R.array.time_minute);
				
				mSimpleListDialog = new SimpleListDialog(mContext);
				mSimpleListDialog.setTitle("选择时间(分)");
				mSimpleListDialog.setTitleLineVisibility(View.GONE);
				mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext,
						mMinutes));
				mSimpleListDialog.setOnSimpleListItemClickListener(PublishPopupWindow.this);
				mSimpleListDialog.show();
				flag = 3;
			}
		}
				);
	

		mLayoutRoot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mBtnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(relation)
				{
					if(judgeTime())
					{
					    CPubHelpMeMsg msg = new CPubHelpMeMsg();
					    msg.setReqItemID(count);
					    msg.setUserID(application.getUser().getUserID());
					    msg.setStartTime(year+"-" + month+"-" + day+" " + mPubStartHtv.getText().toString() +":" + mPubStartMtv.getText().toString()+":00" );
					    msg.setEndTime(year+"-" + month+"-" + day+" " + mPubEndHtv.getText().toString() +":" + mPubEndMtv.getText().toString()+":00" );
					    msg.setReqDetail(edtTxt_details.getText().toString());
					    application.client.sendMessage(msg);
					    MyPubHelpMe myMsg = new MyPubHelpMe(msg, itemName);
						DBManage.addCPubHelpMe(myMsg);
						dismiss();
						showCustomToast("帮我消息发布成功!");
					}
					else
					{
						showCustomToast("时间段设置错误");
					}

				}
				else
				{
					if(judgeTime())
					{
						CPubMeHelpMsg msg = new CPubMeHelpMsg();
						msg.setHelpUserID(application.getUser().getUserID());
						msg.setReqItemID(count);
						msg.setStartTime(year+"-" + month+"-" + day+" " + mPubStartHtv.getText().toString() +":" + mPubStartMtv.getText().toString()+":00" );
					    msg.setEndTime(year+"-" + month+"-" + day+" " + mPubEndHtv.getText().toString() +":" + mPubEndMtv.getText().toString()+":00" );
					    application.client.sendMessage(msg);
					    MyPubMeHelp myMsg = new MyPubMeHelp(msg, itemName);
					    DBManage.addCPubMeHelp(myMsg);
					    dismiss();
					    showCustomToast("我帮消息发布成功!");
					}
					else
					{
						showCustomToast("时间段设置错误");
					}

				}


			
				
				if (mOnSubmitClickListener != null) {
					mOnSubmitClickListener.onClick();
				}
			}
		});
		mBtnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		mRgPubDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			
				if(checkedId == R.id.dialog_publish_rb_today)
				{
					Calendar c = Calendar.getInstance();
				    year = c.get(Calendar.YEAR);
				    month = c.get(Calendar.MONTH)+1;
				    day = c.get(Calendar.DAY_OF_MONTH);
				}
				else if(checkedId == R.id.dialog_publish_rb_tomorrow)
				{
					Calendar c = Calendar.getInstance();
				    year = c.get(Calendar.YEAR);
				    month = c.get(Calendar.MONTH)+1;
				    day = c.get(Calendar.DAY_OF_MONTH)+1;
				}
				else
				{
					Calendar c = Calendar.getInstance();
				    year = c.get(Calendar.YEAR);
				    month = c.get(Calendar.MONTH)+1;
				    day = c.get(Calendar.DAY_OF_MONTH)+2;
				}
				
			}
		});

	
	}

	@Override
	public void init() {
	
		mRgPubDay.check(R.id.dialog_publish_rb_today);
		
	}
	
	public boolean judgeTime()
	{
		boolean ifTimeRight = true;
		
		if(Integer.parseInt(mPubEndHtv.getText().toString()) < Integer.parseInt(mPubStartHtv.getText().toString()))
		{
			ifTimeRight = false;
		}
		else if((Integer.parseInt(mPubEndHtv.getText().toString()) == Integer.parseInt(mPubStartHtv.getText().toString()))&&
				(Integer.parseInt(mPubEndMtv.getText().toString()) <= Integer.parseInt(mPubStartMtv.getText().toString())))
		{
			ifTimeRight = false;
		}
		else
		{
			ifTimeRight = true;
		}
		
		return ifTimeRight;
	}


	@Override
	public void onItemClick(int position) {
		if(flag == 0)
		{
			String text = mHours[position];
			mPubStartHtv.setText(text);
		}
		else if(flag == 1)
		{
			String text = mMinutes[position];
			mPubStartMtv.setText(text);
		}
		else if(flag == 2)
		{
			String text = mHours[position];
			mPubEndHtv.setText(text);
		}
		else 
		{
			String text = mMinutes[position];
			mPubEndMtv.setText(text);
		}

		
	}
	
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(mContext).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

}
