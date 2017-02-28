package cn.hibang.liuzhiwei.message;

import com.example.newhaibang.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;


import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.liuzhiwei.testentity.MyMessage;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;

public class TextMessageItem extends MessageItem implements OnLongClickListener {

	private EmoticonsTextView mEtvContent;

	public TextMessageItem(MyMessage msg, Context context, BaseApplication mApplication) {
		super(msg, context,mApplication);
	}
	

	@Override
	protected void onInitViews() {
		View view = mInflater.inflate(R.layout.message_text, null);
		mLayoutMessageContainer.addView(view);
		mEtvContent = (EmoticonsTextView) view
				.findViewById(R.id.message_etv_msgtext);
		mEtvContent.setText(mMsg.getContent());
		mEtvContent.setOnLongClickListener(this);
		mLayoutMessageContainer.setOnLongClickListener(this);
	}

	@Override
	protected void onFillMessage() {

	}

	@Override
	public boolean onLongClick(View v) {
		System.out.println("长按");
		return true;
	}

}
