package cn.hibang.liuzhiwei.maintabs;

import java.util.ArrayList;
import java.util.List;

import com.example.newhaibang.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import cn.hibang.liuzhiwei.adapter.FeedProfileCommentsAdapter;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.testentity.FeedComment;
import cn.hibang.liuzhiwei.util.DateUtils;
import cn.hibang.liuzhiwei.view.EmoteInputView;
import cn.hibang.liuzhiwei.view.EmoticonsEditText;
import cn.hibang.liuzhiwei.view.EmoticonsTextView;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.HeaderLayout.HeaderStyle;

public class InfoReviewActivity extends BaseActivity implements
OnItemClickListener,OnClickListener,OnTouchListener{
	
	private ListView mLvList;//评论内容列表
	private FeedProfileCommentsAdapter mAdapter;
	private List<FeedComment> mComments = new ArrayList<FeedComment>();
    private EmoticonsTextView mEtvEditerTitle;
    private ImageView mIvEmote;
    private Button mBtnSend;
    private EmoticonsEditText mEetEditer;
    private EmoteInputView mInputView;
    private HeaderLayout mHeaderLayout;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_review);
		initViews();
		initEvents();
		init();
		
	}

	@Override
	protected void initViews() {
		
		mHeaderLayout = (HeaderLayout) findViewById(R.id.feedprofile_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("用户评论", null);
	
		mLvList = (ListView) findViewById(R.id.feedprofile_lv_list);
		
		mLvList = (ListView) findViewById(R.id.feedprofile_lv_list);
		mEtvEditerTitle = (EmoticonsTextView) findViewById(R.id.feedprofile_etv_editertitle);
		mIvEmote = (ImageView) findViewById(R.id.feedprofile_iv_emote);
		mBtnSend = (Button) findViewById(R.id.feedprofile_btn_send);
		mEetEditer = (EmoticonsEditText) findViewById(R.id.feedprofile_eet_editer);
		mInputView = (EmoteInputView) findViewById(R.id.feedprofile_eiv_input);
	}

	@Override
	protected void initEvents() {

		mLvList.setOnItemClickListener(this);
		mEetEditer.setOnTouchListener(this);
		mBtnSend.setOnClickListener(this);
		mIvEmote.setOnClickListener(this);
	
		
	}
	
	private void init()
	{
		mInputView.setEditText(mEetEditer);
		
		for(int i = 0;i < 8;i++)
		{
			mComments.add(new FeedComment("田贝", "", "用来测试的评论", DateUtils.formatDate(
					InfoReviewActivity.this,
					System.currentTimeMillis())));
		}
		
		mAdapter = new FeedProfileCommentsAdapter(mApplication,
				InfoReviewActivity.this, mComments);
		mLvList.setAdapter(mAdapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.feedprofile_iv_emote:
			mEetEditer.requestFocus();
			if (mInputView.isShown()) {
				showKeyBoard();
			} else {
				hideKeyBoard();
				mInputView.setVisibility(View.VISIBLE);
			}
			
			break;
		case R.id.feedprofile_btn_send:
			String content = mEetEditer.getText().toString().trim();
			if (TextUtils.isEmpty(content)) {
				showCustomToast("请输入评论内容");
				mEetEditer.requestFocus();
			} else {
				String reply = null;
				if (mEtvEditerTitle.isShown()) {
					reply = mEtvEditerTitle.getText().toString().trim();
				}
				content = TextUtils.isEmpty(reply) ? content : reply + content;
				FeedComment comment = new FeedComment("测试用户",
						"nearby_people_other", content, DateUtils.formatDate(
								InfoReviewActivity.this,
								System.currentTimeMillis()));
				mComments.add(0, comment);
				mAdapter.notifyDataSetChanged();
			}
			mEtvEditerTitle.setText(null);
			mEtvEditerTitle.setVisibility(View.GONE);
			mEetEditer.setText(null);
			mInputView.setVisibility(View.GONE);
			hideKeyBoard();

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		if (v.getId() == R.id.feedprofile_eet_editer) {
			showKeyBoard();
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		if (mInputView.isShown()) {
			mInputView.setVisibility(View.GONE);
		} else {
			finish();
		}
	}
	
	private void showKeyBoard() {
		if (mInputView.isShown()) {
			mInputView.setVisibility(View.GONE);
		}
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.showSoftInput(mEetEditer, 0);
	}

	private void hideKeyBoard() {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(InfoReviewActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}

	
	

}
