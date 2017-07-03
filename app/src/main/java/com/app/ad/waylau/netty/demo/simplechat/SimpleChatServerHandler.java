package com.app.ad.waylau.netty.demo.simplechat;

import com.app.ad.entity.TcpEvent;
import com.app.ad.global.Constant;


import android.text.TextUtils;
import de.greenrobot.event.EventBus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务端 channel
 * 
 * @author waylau.com
 * @date 2015-2-16
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)
	
	/**
	 * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
	 * A closed Channel is automatically removed from the collection,
	 */
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
    	System.out.println("SimpleChatClient:"+ incoming.remoteAddress() + " 加入\n");
        EventBus.getDefault().post(new TcpEvent(Constant.ACTION_ADD,incoming.remoteAddress().toString() ));
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+ incoming.remoteAddress() + " 离开\n");
        EventBus.getDefault().post(new TcpEvent( Constant.ACTION_REMOVE,incoming.remoteAddress().toString() ));
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }
    
 
    
    @Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
		Channel incoming = ctx.channel();
    	if(TextUtils.isEmpty(s)){return;}
    	System.out.println("SimpleChatClientRead0:recv data "+s);
    	if(s.contains("STX@nzjl")){
    		for (Channel channel : channels) {
                if (channel == incoming){
                	  System.out.println("notify "+incoming.remoteAddress() +"msg");
                	  EventBus.getDefault().post(new TcpEvent(channel,Constant.ACTION_READ,incoming.remoteAddress().toString(),s));
                	  System.out.println("发送通知");
                      break;
                }
            }
    	}
		
	}
  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        EventBus.getDefault().post(new TcpEvent(Constant.ACTION_ACTIVE,incoming.remoteAddress().toString() ));
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        EventBus.getDefault().post(new TcpEvent(Constant.ACTION_INACTIVE,incoming.remoteAddress().toString() ));
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
    	Channel incoming = ctx.channel();
    	EventBus.getDefault().post(new TcpEvent(Constant.ACTION_EXCEPTION,incoming.remoteAddress().toString() ));
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}