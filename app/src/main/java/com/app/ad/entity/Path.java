
/**   
 * @Title: Path.java 
 * @Package: com.app.ad.entity 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年12月12日 下午4:02:32 
 * @version 1.3.1 
 */


package com.app.ad.entity;

import java.io.Serializable;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年12月12日 下午4:02:32 
 * @version V1.3.1
 */

public class Path implements Serializable{
	
	
	/** @Fields serialVersionUID: */
	  	
	private static final long serialVersionUID = 1L;
	private String name;
	private String filePath;
	
	public Path(){
		
	}
	
	public Path(String name,String filePath){
		this.name = name;
		this.filePath =filePath;
	}

	
	/**
	 * @return name
	 */
	
	public String getName() {
		return name;
	}

	
	/** 
	 * @param name 要设置的 name 
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return filePath
	 */
	
	public String getFilePath() {
		return filePath;
	}

	
	/** 
	 * @param filePath 要设置的 filePath 
	 */
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
	
	
}
