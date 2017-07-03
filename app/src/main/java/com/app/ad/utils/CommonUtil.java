
/**   
 * @Title: CommonUtil.java 
 * @Package: com.senthink.express.util 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年4月14日 下午1:39:31 
 * @version 1.3.1 
 */


package com.app.ad.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** 
 * @Description 
 * @author Selena Wong
 * @date 2016年4月14日 下午1:39:31 
 * @version V1.3.1
 */

public class CommonUtil {
	
	 /**
     * Check whether Internet connection is enabled on the device
     *
     * @param context
     * @return
     */
    public static final boolean checkNetwork(Context context) {
        if (context != null) {
            boolean result = true;
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                result = false;
            }
            return result;
        } else {
            return false;
        }
    }
    
	//检查网络是否正常
	public static boolean isNetworkAvailable(Context context){

		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean checkNetWorkState(Context context){
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
				return false;
		} else {
				// 获取NetworkInfo对象
				NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
				if (networkInfo != null && networkInfo.length > 0) {
					for (int i = 0; i < networkInfo.length; i++) {
					 // 判断当前网络状态是否为连接状态
						if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
		}
		return false;	
	}

}
