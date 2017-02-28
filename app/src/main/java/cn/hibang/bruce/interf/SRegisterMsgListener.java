package cn.hibang.bruce.interf;

import cn.hibang.huxing.servermessage.SRegisterMsg;


public interface SRegisterMsgListener extends MsgListener{

	public void onMsgReveived(SRegisterMsg sRegisterMsg);
}
