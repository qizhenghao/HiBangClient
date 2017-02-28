package com.elife.ehelp.view;

import com.example.newhaibang.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

 
public class DialogLoading { 
	Dialog mAlertDialog;
	Context context;
	String title;
	
	public DialogLoading(Context context,String title) {
		this.context = context;
		this.title = title;
		init();
	}
	
	private void init() {
		mAlertDialog = new Dialog(context);
		mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

		LinearLayout alertLayout = (LinearLayout) LinearLayout.inflate(
				context, R.layout.waiting_dialog, null);

		TextView alertHeader = (TextView) alertLayout
				.findViewById(R.id.waiting_title);
		alertHeader.setText(title);
		
		mAlertDialog.setCanceledOnTouchOutside(false);
		mAlertDialog.setContentView(alertLayout);
	}
	
	public void show(){
		mAlertDialog.show();
	}
	
	public void close() {
		mAlertDialog.cancel();
	}
}

