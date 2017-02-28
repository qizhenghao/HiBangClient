package cn.hibang.bruce.netty;

import org.jboss.netty.buffer.ChannelBuffer;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.config.JSONContext;
import cn.hibang.bruce.exception.PackageErrorException;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.SericalidClassManager;
import cn.hibang.huxing.servermessage.SLoginMsg;
import cn.hibang.huxing.servermessage.SPhotoRequestMsg;


enum HIBangPackage {
	READ_LENGTH, PHOTO_FLAG, READ_CONTENT;
}


public class PackageDecoder extends ReplayingDecoder<HIBangPackage> {

	private int length;
	private int bIsPacketPhoto = 0;
	
	public PackageDecoder() {
		super(HIBangPackage.PHOTO_FLAG);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buf, HIBangPackage state) throws Exception {
		int contentIndex = 0;
		long strSerialId;
		IMessage recvMsgObj = null;
		switch (state) {
		case PHOTO_FLAG:
			bIsPacketPhoto = buf.readInt();
			System.out.println("PHOTO :" + bIsPacketPhoto);
			checkpoint(HIBangPackage.READ_LENGTH);
		case READ_LENGTH:
			length = buf.readInt();
			System.out.println("LENGTH:" + length);
			checkpoint(HIBangPackage.READ_CONTENT);
		case READ_CONTENT:
			
			String frmString = null;
			if(bIsPacketPhoto == 0)
			{
				ChannelBuffer frame = buf.readBytes(length);
				frmString = new String(frame.array(), Config.PACKAGE_ENCODE);
				if(-1 == (contentIndex = frmString.indexOf('{')))
					throw new PackageErrorException();								
				strSerialId = Long.parseLong(frmString.substring(0, contentIndex));
				Class clz = SericalidClassManager.getInstance().findClassById(strSerialId);
				IMessage msg = (IMessage) clz.newInstance();
				String str = frmString.substring(contentIndex);
				recvMsgObj = (IMessage) JSONContext.mapper.readValue(str, clz);
			}
			else 
			{
				int userId = buf.readInt();
				ChannelBuffer frame = buf.readBytes(length);
				recvMsgObj = new SPhotoRequestMsg();
				((SPhotoRequestMsg)recvMsgObj).setUserId(userId);
				((SPhotoRequestMsg)recvMsgObj).setPhoto(frame.array());
			}
			checkpoint(HIBangPackage.PHOTO_FLAG);
			return recvMsgObj;
		default:
			throw new Error("Shouldn't reach here.");
		}
	}

}
