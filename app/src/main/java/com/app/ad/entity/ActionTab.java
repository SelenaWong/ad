/**    
 * 文件名：Table.java    
 *    
 * 版本信息：    
 * 日期：2015年11月3日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.entity;

import java.io.Serializable;

/**
 * 
 * 类名称：ActionTable 类描述： 创建人：wesley 创建时间：2015年11月3日 上午9:31:56
 * 
 */
public class ActionTab extends Table implements Serializable {

	private Action attributes;

	public ActionTab() {

	}

	public ActionTab(String name, int type, int id, Action attributes) {
		super(name, type, id);
		this.attributes = attributes;
	}

	/**
	 * attributes
	 * 
	 * @return the attributes
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public Action getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Action attributes) {
		this.attributes = attributes;
	}

}
