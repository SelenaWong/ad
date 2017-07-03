/**    
 * 文件名：DeviceTab.java    
 *    
 * 版本信息：    
 * 日期：2015年11月2日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.entity;

import java.io.Serializable;

import android.util.Log;

/**
 * 
 * 类名称：DeviceTab 类描述： 创建人：wesley 创建时间：2015年11月2日 上午10:49:54
 * 
 */
public class DeviceTab implements Serializable {

	private final static String TAG = "DynamicActionBar";
	private String serverIP;
	private String serverPort;
	private String TabType;// 标签页类型
	private String DevType;// 设备类型
	private String TabName;
	private String TabId;

	public DeviceTab() {

	}

	public DeviceTab(String serverIP, String serverPort, String tabType,
			String devType, String tabName, String tabId) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.TabType = tabType;
		this.DevType = devType;
		this.TabName = tabName;
		this.TabId = tabId;
	}

	public void Print() {

		Log.i(TAG, "serverIP=" + serverIP + "serverPort=" + serverPort
				+ "tabType=" + TabType + "TabName=" + TabName + "TabId="
				+ TabId);
	}

	/**
	 * serverIP
	 * 
	 * @return the serverIP
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getServerIP() {
		return serverIP;
	}

	/**
	 * @param serverIP
	 *            the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * serverPort
	 * 
	 * @return the serverPort
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * tabType
	 * 
	 * @return the tabType
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getTabType() {
		return TabType;
	}

	/**
	 * @param tabType
	 *            the tabType to set
	 */
	public void setTabType(String tabType) {
		TabType = tabType;
	}

	public String getDevType() {
		return DevType;
	}

	/**
	 * @param tabType
	 *            the tabType to set
	 */
	public void setDevType(String devType) {
		DevType = devType;
	}

	/**
	 * tabName
	 * 
	 * @return the tabName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getTabName() {
		return TabName;
	}

	/**
	 * @param tabName
	 *            the tabName to set
	 */
	public void setTabName(String tabName) {
		TabName = tabName;
	}

	/**
	 * tabId
	 * 
	 * @return the tabId
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getTabId() {
		return TabId;
	}

	/**
	 * @param tabId
	 *            the tabId to set
	 */
	public void setTabId(String tabId) {
		TabId = tabId;
	}

}
