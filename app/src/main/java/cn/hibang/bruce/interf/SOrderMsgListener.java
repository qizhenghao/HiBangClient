package cn.hibang.bruce.interf;

import cn.hibang.huxing.servermessage.SOrderMsg;

public interface SOrderMsgListener {

	public void onSOrderMsgReceived(SOrderMsg msg);
}
