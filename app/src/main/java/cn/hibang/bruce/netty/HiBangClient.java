package cn.hibang.bruce.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import cn.hibang.huxing.clientmessage.IMessage;

public class HiBangClient {

	private Channel channel = null;
	private ChannelFuture channelFuture = null;
	private ClientBootstrap bootstrap = null;
	/**
	 * <p>
	 * Function : send message to server
	 * 
	 * @author Bruce Qi
	 *         <p>
	 *         StartTime: 2014-3-4 12:06:29
	 *         <p>
	 *         LastTime :
	 * 
	 * @param IMessage`s subclass
	 */
	public void sendMessage(IMessage msg) {
		channel.write(msg);
	}

	public void start(String ip, int port) {
		
		// Configure the client.
		System.setProperty("java.net.preferIPv4Stack", "true");  
        System.setProperty("java.net.preferIPv6Addresses", "false"); 
        bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		// Set up the event pipeline factory.
		// bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
		bootstrap.getPipeline().addLast("decoder", new PackageDecoder());
		bootstrap.getPipeline().addLast("encoder", new PackageEncoder());
		bootstrap.getPipeline().addLast("handler", new ClientHandler());
		
		bootstrap.setOption("tcpNoDelay", true);  
        bootstrap.setOption("keepAlive", true); 
        bootstrap.setOption("child.linger", 1);
        
		// Start the connection attempt.
		channelFuture = bootstrap.connect(new InetSocketAddress(ip, port));
		// Wait until the connection is closed or the connection attempt fails.
		channelFuture.awaitUninterruptibly();
		
		channel = channelFuture.awaitUninterruptibly().getChannel();
	}
	
	
	public void stop(){
//		channelFuture.awaitUninterruptibly();
//		if (!channelFuture.isSuccess()) {
//			channelFuture.getCause().printStackTrace();
//		}
//		channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();

		bootstrap.releaseExternalResources();
	}
	
}
