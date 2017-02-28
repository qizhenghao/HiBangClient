package cn.hibang.liuzhiwei.maintabs;



import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.SHelpMeMsgListener;
import cn.hibang.bruce.interf.SMeHelpMsgListener;
import cn.hibang.bruce.interf.SPhotoRequestMsgListener;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import cn.hibang.liuzhiwei.android.BaseActivity;
import cn.hibang.liuzhiwei.view.HeaderLayout;
import cn.hibang.liuzhiwei.view.HeaderSpinner;
import cn.hibang.liuzhiwei.view.SwitcherButton.SwitcherButtonState;
import cn.hibang.liuzhiwei.view.SwitcherButton.onSwitcherButtonClickListener;

import com.example.newhaibang.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

public class MessageActivity extends BaseActivity implements
		SMeHelpMsgListener, SHelpMeMsgListener, SPhotoRequestMsgListener {

	private HeaderLayout mHeaderLayout;
	private HeaderSpinner mHeaderSpinner;
	private HelpMeMessageFragment mMessageFragment;
	private MeHelpMessageFragment mHelperFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		SMsgManage.getManager().setCurrContext(this);
		SMsgManage.getManager().setsMeHelpMsgListener(this);
		SMsgManage.getManager().setSHelpMeMsgListener(this);
		SMsgManage.contextMap.put(Config.TAG_MessageActivity, this);
		initViews();
		init();
	}

	@Override
	protected void onResume() {
		SMsgManage.getManager().setCurrContext(this);
		super.onResume();
	}

	public void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.message_header);
		mHeaderLayout.myMsgTitle("消息", "帮我", "我帮",
				new OnSwitcherButtonClickListener());

	}

	public void init() {
		mMessageFragment = new HelpMeMessageFragment(mApplication, this, this);
		mHelperFragment = new MeHelpMessageFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.message_layout_content, mMessageFragment).commit();
	}

	public class OnSwitcherButtonClickListener implements
			onSwitcherButtonClickListener {

		@Override
		public void onClick(SwitcherButtonState state) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.setCustomAnimations(R.anim.fragment_fadein,
					R.anim.fragment_fadeout);
			switch (state) {
			case LEFT:
				// mHeaderLayout.myMsgTitle("消息", "我帮", "帮我", new
				// OnSwitcherButtonClickListener());
				ft.replace(R.id.message_layout_content, mMessageFragment)
						.commit();
				break;

			case RIGHT:
				// mHeaderLayout.myMsgTitle("消息", "我帮", "帮我", new
				// OnSwitcherButtonClickListener());
				ft.replace(R.id.message_layout_content, mHelperFragment)
						.commit();
				break;
			}
		}

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	private final int HELP_ME_MSG_RECEIVED = 1;
	private final int ME_HELP_MSG_RECEIVED = 2;
	private final int PHOTO_MSG_RECEIVED = 3;

	@Override
	public void onHelpMeMsgReveived(SHelpMeMsg sHelpMeMsg) {
		Message msg = new Message();
		msg.what = this.HELP_ME_MSG_RECEIVED;
		msg.obj = sHelpMeMsg;
		handler.sendMessage(msg);
	}

	@Override
	public void onMeHelpMsgReveived(SMeHelpMsg sMeHelpMsg) {
		Message msg = new Message();
		msg.what = this.ME_HELP_MSG_RECEIVED;
		msg.obj = sMeHelpMsg;
		handler.sendMessage(msg);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HELP_ME_MSG_RECEIVED:
				SHelpMeMsg helpMeMsg = (SHelpMeMsg) msg.obj;
				// refresh the UI, For Liu zhi wei
				mMessageFragment.setHelpMeMsg(helpMeMsg);
				break;
			case ME_HELP_MSG_RECEIVED:
				SMeHelpMsg meHelpMsg = (SMeHelpMsg) msg.obj;
				// refresh the UI, For Liu zhi wei
				mHelperFragment.setMeHelpMsg(meHelpMsg);
				break;
			case PHOTO_MSG_RECEIVED:
				mMessageFragment.refrsh();
				mHelperFragment.refrsh();
			}
		}
	};

	@Override
	public void onSPhotoReqMsgReveived() {
		Message msg = new Message();
		msg.what = this.ME_HELP_MSG_RECEIVED;
		handler.sendMessage(msg);
		
	}
	

	private long mExitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
