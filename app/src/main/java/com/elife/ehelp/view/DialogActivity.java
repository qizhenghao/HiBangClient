package com.elife.ehelp.view;

import cn.hibang.liuzhiwei.view.HandyTextView;

import com.example.newhaibang.R;

import android.app.Activity;
import android.os.Bundle;

public class DialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_dialog);
		String title = getIntent().getStringExtra("title");
		((HandyTextView)findViewById(R.id.waiting_title)).setText(title);
	}
}
