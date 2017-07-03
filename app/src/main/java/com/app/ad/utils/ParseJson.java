
/**   
 * @Title: ParseJson.java 
 * @Package: com.app.ad.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年7月8日 上午9:57:47 
 * @version 1.3.1 
 */


package com.app.ad.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.ad.application.AdApplication;
import com.app.ad.entity.Action;
import com.app.ad.entity.ActionTab;
import com.app.ad.entity.DeviceTab;

import android.text.TextUtils;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年7月8日 上午9:57:47 
 * @version V1.3.1
 */

public class ParseJson {
	
	
	public static int ParseActionState(String input){
	  if(TextUtils.isEmpty(input)){
			return -1;
	  }
	  try{
			ActionTab at = (ActionTab) FastJsonUtil.DeSerialize(input, ActionTab.class);
			if(at!=null){
				Action ac = at.getAttributes();
				if(ac!=null){
					return ac.getState();
				}
			}
	  }catch(Exception ex){
		  ex.printStackTrace();
	  }
		return -1;
	}
	
	
	public static boolean IsRetContainData(JSONObject obj, String key) {
		boolean blSuccess = false;
		if (obj.has(key) && !obj.isNull(key)) {
			blSuccess = true;
		}
		return blSuccess;
	}

	public static boolean IsRetContainData(JSONObject obj, String[] keys) {
		boolean blSuccess = true;
		for (int i = 0; i < keys.length; i++) {
			if (!obj.has(keys[i]) || !obj.isNull(keys[i])) {
				blSuccess = false;
				break;
			}
		}
		return blSuccess;
	}

	
	/**
	 * 智能开关状态返回值解析
	 */
	public static String ParseTableId(String data) {
		String id = "";
		try {
			JSONObject obj = new JSONObject(data);
			if (!IsRetContainData(obj, "table")) {
				return id;
			}
			JSONObject tableObj = obj.getJSONObject("table");
			if (!IsRetContainData(tableObj, "id")) {
				return null;
			}
			id = Integer.toString(tableObj.getInt("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}


}
