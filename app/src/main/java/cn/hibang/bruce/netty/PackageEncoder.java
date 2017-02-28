package cn.hibang.bruce.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import cn.hibang.bruce.config.Config;
import cn.hibang.huxing.clientmessage.CPhotoUpdateMsg;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.servermessage.SPhotoRequestMsg;

public class PackageEncoder extends SimpleChannelHandler{

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		/* Create a newly ChannelBuffer */
		ChannelBuffer frmBuf = ChannelBuffers.dynamicBuffer();		
		/* Get a IMessage Object */
		IMessage sMsg = (IMessage) e.getMessage();
		/* Transfer the object to a byte array */
		byte[] frameContent = null;
				
		if((sMsg instanceof CPhotoUpdateMsg))
		{
			frmBuf.writeInt(1);
			frmBuf.writeInt(((CPhotoUpdateMsg)sMsg).getPhoto().length);
			frmBuf.writeInt(((CPhotoUpdateMsg)sMsg).getUserId());
			frmBuf.writeBytes(((CPhotoUpdateMsg)sMsg).getPhoto(), 0, ((CPhotoUpdateMsg)sMsg).getPhoto().length);
		}
		else
		{
			frameContent = sMsg.toString().getBytes(Config.PACKAGE_ENCODE);
			frmBuf.writeInt(0);
			/* Write the content length to the buffer */
			frmBuf.writeInt(frameContent.length);
			/* Write the content to the buffer */
			frmBuf.writeBytes(frameContent, 0, frameContent.length);
		}
				
		
		Channels.write(ctx, e.getFuture(), frmBuf);
	}
}