/**    
 * 文件名：FastJsonUtil.java    
 *    
 * 版本信息：    
 * 日期：2015年7月29日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.utils;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**    
 *      
 * 类名称：FastJsonUtil    
 * 类描述：    
 * 创建人：wesley    
 * 创建时间：2015年7月29日 下午6:57:29           
 *     
 */
public class FastJsonUtil {
	  
	//下面是FastJson的简介：常用的方法！  
	//  Fastjson API入口类是com.alibaba.fastjson.JSON，常用的序列化操作都可以在JSON类上的静态方法直接完成。  
	//  public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray   
	//  public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject      
	//  public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean   
	//  public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray  	 
	//  public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合   
	//  public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本   
	//  public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本   
	//  public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray（和上面方法的区别是返回值是不一样的） 
	
	
	/**对象或是对象列表转换为JSON格式
	 * 直接可以将
	 */
	public static String  Serialize(Object obj){
	
		return JSON.toJSONString(obj);
	}
	/**
	 * 将json类的字符串解析为类对象
	 */
	public static <T> Object DeSerialize(String input ,Class<T> clazz){
		
		Object obj=JSON.parseObject(input,clazz );
		return obj;
	}
	/**
	 * 将jsonArray数据，转化为List<T>
	 */	public static <T> List<T> DeSerializeArray(String input,Class<T> clazz){

		 return JSON.parseArray(input, clazz);
	}
	public static <T> List<T> DeSerializeArray(JSONArray jsonArray,Class<T> clazz){
		
		 String input = jsonArray.toJSONString();
		 return JSON.parseArray(input, clazz);
	}
	/**
	 * String转换为JSONArray
	 */
	public static JSONArray ToJSONArray( String input){
		JSONArray jsonArr = JSON.parseArray(input);
		return  jsonArr;
	}
}
