
/**   
 * @Title: Convert.java 
 * @Package: com.app.ad.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年6月20日 下午1:43:13 
 * @version 1.3.1 
 */


package com.app.ad.utils;

import android.text.TextUtils;

/** 
 * @Description 类型转换辅助类
 * @author Selena Wong
 * @date 2016年6月20日 下午1:43:13 
 * @version V1.3.1
 */

public class Convert {
	
	public static int ConvertToInt(Object source ,int defaultValue){
		if(source ==null|| TextUtils.isEmpty(source.toString().trim())){
			return defaultValue;
		}
		try{
			return Integer.valueOf(source.toString().trim());
		}catch(Exception ex){
			return Double.valueOf(source.toString().trim()).intValue();
		}
	}

	
}
