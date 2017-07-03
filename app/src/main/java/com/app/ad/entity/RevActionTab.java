
/**   
 * @Title: RevActionTab.java 
 * @Package: com.app.ad.entity 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年7月25日 上午9:29:44 
 * @version 1.3.1 
 */


package com.app.ad.entity;

/** 
 * @Description  返回给客户端的数据
 * @author Selena Wong
 * @date 2016年7月25日 上午9:29:44 
 * @version V1.3.1
 */

public class RevActionTab {
	
	private String type;
	private String serialNum;
	private String appId;
	private int code;
	private String msg;
	private ActionTab table;
	
	public RevActionTab(){
		
	}
	/**
	 * @return type
	 */
	
	public String getType() {
		return type;
	}


	
	/** 
	 * @param type 要设置的 type 
	 */
	
	public void setType(String type) {
		this.type = type;
	}


	
	/**
	 * @return serialNum
	 */
	
	public String getSerialNum() {
		return serialNum;
	}


	
	/** 
	 * @param serialNum 要设置的 serialNum 
	 */
	
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


	
	/**
	 * @return appId
	 */
	
	public String getAppId() {
		return appId;
	}


	
	/** 
	 * @param appId 要设置的 appId 
	 */
	
	public void setAppId(String appId) {
		this.appId = appId;
	}


	
	/**
	 * @return code
	 */
	
	public int getCode() {
		return code;
	}


	
	/** 
	 * @param code 要设置的 code 
	 */
	
	public void setCode(int code) {
		this.code = code;
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
	 * @return table
	 */
	
	public ActionTab getTable() {
		return table;
	}


	
	/** 
	 * @param table 要设置的 table 
	 */
	
	public void setTable(ActionTab table) {
		this.table = table;
	}
	
	
	
}
