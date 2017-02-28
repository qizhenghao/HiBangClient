package cn.hibang.liuzhiwei.popupwindow;

import com.example.newhaibang.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.hibang.liuzhiwei.android.BasePopupWindow;

public class PublishSelectPopupWindow extends BasePopupWindow{
	
	private LinearLayout mLayoutRoot;// 根布局
	private Button mBtnTradding;// 查看交易中按钮
	private Button mBtnTraded;// 查看交易完成按钮
	
	public PublishSelectPopupWindow(Context context) {
		super(LayoutInflater.from(context).inflate(
				R.layout.include_dialog_publish_filter, null),
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.Popup_Animation_PushDownUp);
	}


	@Override
	public void initViews() {
	
		mLayoutRoot = (LinearLayout) findViewById(R.id.dialog_publish_layout_root);
		//mRgPublishFilter = (RadioGroup) findViewById(R.id.dialog_publish_details);
		mBtnTradding = (Button) findViewById(R.id.button_tradding);
		mBtnTraded = (Button) findViewById(R.id.button_traded);
		
	}

	@Override
	public void initEvents() {
	
		mLayoutRoot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		//查看交易中按钮监听
		mBtnTradding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (mOnSubmitClickListener != null) {
					mOnSubmitClickListener.onClick();
				}
			}
		});
		
		//查看交易完成按钮监听
		mBtnTraded.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				
				if (mOnFinishClickListener != null) {
					mOnFinishClickListener.onClick();
				}
			}
		});
		
		//单选按钮监听
//		mRgPublishFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// 暂时不做任何操作
//				switch (checkedId) {
//				case R.id.dialog_publish_1:
//			
//					break;
//				case R.id.dialog_publish_2:
//					
//					break;
//				case R.id.dialog_publish_3:
//					
//					break;
//				case R.id.dialog_publish_4:
//				
//					break;
//					
//				default:
//					break;
//				}
//				
//			}
//		});
		
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	

}
