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
 * 类名称：Table 类描述： 创建人：wesley 创建时间：2015年11月3日 上午9:50:28
 * 
 */
public class Table implements Serializable {
	private String name;
	private int type;
	private int id;

	public Table() {
		this.type = 0;
	}

	public Table(String name, int type, int id) {
		this.name = name;
		this.type = type;
		this.id = id;
	}

	/**
	 * name
	 * 
	 * @return the name
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * type
	 * 
	 * @return the type
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * id
	 * 
	 * @return the id
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
