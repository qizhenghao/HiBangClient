package cn.hibang.bruce.service;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.IMessage;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class NettyService extends Service implements Runnable{

	public static final String ACTION_SEND_MSG = "send";
	public static final String ACTION_AGAIN = "again";
	public static final String ACTION_STOP = "stop";
//	private HiBangClient client = null;
	private Binder binder = null;
	private Thread nettyThread;
	private BaseApplication application = null;
	@Override
	public void onCreate() {
		super.onCreate();
		binder = new NettyBinder();
		application = (BaseApplication) getApplication();
//		client = new HiBangClient();
		connectServer();
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		if(intent == null) {
			return;
		}
		String action = intent.getAction();
		if(action.equals("RESTARTCIENT")) {
//			connectServer();
		}
	}
	 
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	@Override
	public IBinder onBind(Intent arg0) {
		
		return binder;
	}

	public void connectServer(){
		nettyThread = new Thread(NettyService.this);
		nettyThread.start();
	}
	
	public void sendMsgToServer(IMessage msg) {
		application.client.sendMessage(msg);
	}
	
	public class NettyBinder extends Binder {
        public NettyService getService() {
            return NettyService.this;
        }
    }

	@Override
	public void run() {
		application.client.start(Config.SERVER_IP, Config.SERVER_PORT);
	}
	
}
