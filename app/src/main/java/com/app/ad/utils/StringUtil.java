/**    
 * 文件名：StringUtil.java    
 *    
 * 版本信息：    
 * 日期：2015年7月22日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * 
 * 类名称：StringUtil 类描述： 创建人：wesley 创建时间：2015年7月22日 下午5:57:58
 * 
 */
public class StringUtil {

	/**
	 * 验证是否是有效的数据
	 */
	public static boolean IsValiateData(String repEx, String input) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(repEx);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			flag = true;
		}
		return flag;
	}

	public static boolean IsValidateIP(String input) {
		if (input.isEmpty()) {
			return false;
		}
		String repEx = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
				+ "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
				+ "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
				+ "\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
		return IsValiateData(repEx, input);
	}

	public static boolean IsInteger(String input) {
		String repEx = "[0-9]|[0-9]*";
		if (input.startsWith("-") || input.startsWith("+")) {
			String subString = input.substring(1);
			Log.i("appLog", "subString=" + subString);
			return IsValiateData(repEx, subString);
		} else {
			return IsValiateData(repEx, input);
		}
	}

	public static int GetSId(String input) {

		if (input.startsWith("-")) {
			String subString = input.substring(1);
			int result = Integer.parseInt(subString);
			return (-1) * result;
		} else if (input.startsWith("+")) {
			String subString = input.substring(1);
			int result = Integer.parseInt(subString);
			return result;
		} else {
			int result = Integer.parseInt(input);
			return result;
		}
	}
}
