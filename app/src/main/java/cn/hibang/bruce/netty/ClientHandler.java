package cn.hibang.bruce.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import android.content.Context;
import android.util.Log;


import cn.hibang.bruce.control.SMsgManage;
import cn.hibang.bruce.interf.MsgListener;
import cn.hibang.bruce.interf.SChatMessageListener;
import cn.hibang.bruce.utils.MyToast;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.huxing.servermessage.SRecommendListMsg;
import cn.hibang.liaohongxian.activity.LoginActivity;

public class ClientHandler extends SimpleChannelHandler{
	
	

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		SMsgManage.getManager().messageReceived(e);
	}
	
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
//		MyToast.ShowMessage(SMsgManage.getManager().getCurrContext(),"有异常");
		Log.i(ClientHandler.class.getName(), e.toString());
		super.exceptionCaught(ctx, e);
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		MyToast.ShowMessage(SMsgManage.getManager().getCurrContext(), e.toString());
		Log.i(ClientHandler.class.getName(), e.toString());
		super.channelConnected(ctx, e);
	}
}
