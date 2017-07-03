
/**   
 * @Title: MsgEvent.java 
 * @Package: com.app.ad.entity 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年7月20日 上午10:06:10 
 * @version 1.3.1 
 */


package com.app.ad.entity;

import io.netty.channel.Channel;

/** 
 * @Description 接收到的消息
 * @author Selena Wong
 * @date 2016年7月20日 上午10:06:10 
 * @version V1.3.1
 */

public class TcpEvent {
	
	private Channel channel;
	private String ip;
	private String msg;
	private int actionType;

	public TcpEvent(Channel channel,int ActionType,String ip,String msg){
		this.channel = channel;
		this.actionType = ActionType;
		this.ip = ip;
		this.msg = msg;
	}
	
	public TcpEvent(int ActionType,String ip){
		this.channel = null;
		this.actionType = ActionType;
		this.ip = ip;
		this.msg = null;
	}

	public TcpEvent(){
		
	}
	
	
	
	/**
	 * @return channel
	 */
	
	public Channel getChannel() {
		return channel;
	}

	
	/** 
	 * @param channel 要设置的 channel 
	 */
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return ip
	 */
	
	public String getIp() {
		return ip;
	}

	
	/** 
	 * @param ip 要设置的 ip 
	 */
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return msg
	 */
	
	public String getMsg() {
		return msg;
	}

	
	/** 
	 * @param msg 要设置的 msg 
	 */
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	/**
	 * @return actionType
	 */
	
	public int getActionType() {
		return actionType;
	}

	
	/** 
	 * @param actionType 要设置的 actionType 
	 */
	
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

}
