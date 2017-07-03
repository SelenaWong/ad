
/**   
 * @Title: NetworkUtil.java 
 * @Package: com.app.ad.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年10月23日 下午2:09:20 
 * @version 1.3.1 
 */


package com.app.ad.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年10月23日 下午2:09:20 
 * @version V1.3.1
 */

public class NetUtil {
	
	/***
	 * 
	 * @Description 获取ip相关信息 
	 * @author lenovo
	 * @return
	 */
	public static String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress
							.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += inetAddress.getHostAddress();
						Log.i("NetworkUtil","ip="+ip );
						break;
					}
				}
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
			Log.i("NetworkUtil","ip="+ip );
		}
		return ip;
	}
	
	
	 /***
	  * 
	  * @Description 网络连接状态检测 
	  * @author lenovo
	  * @param activity
	  * @return
	  */
	 public static boolean isNetworkAvailable(Activity activity){
				Context context = activity.getApplicationContext();
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
