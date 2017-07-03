/**    
 * 文件名：Attribute.java    
 *    
 * 版本信息：    
 * 日期：2015年11月2日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.entity;

import java.io.Serializable;

/**
 * 
 * 类名称：Attribute 类描述： 创建人：wesley 创建时间：2015年11月2日 下午5:21:01
 * 
 */
public class Action implements Serializable {

	private int state;// 0:开，1：关

	public Action() {

	}

	public Action(int state) {
		this.state = state;
	}

	/**
	 * state
	 * 
	 * @return the state
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

}
