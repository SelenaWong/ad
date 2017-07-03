
/**   
 * @Title: AdApplication.java 
 * @Package: com.app.ad.activity 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年6月20日 下午2:19:50 
 * @version 1.3.1 
 */


package com.app.ad.application;
import com.app.ad.utils.ConnectionLog;

import android.app.Application;
import android.util.Log;

/** 
 * @Description 自定义Application类
 * @author Selena Wong
 * @date 2016年6月20日 下午2:19:50 
 * @version V1.3.1
 */

public class AdApplication extends Application {
  
	public final static String APPID = "123456";
	public static int serialNum = 0;
	public final static int TYPE_HEARTBEAT = 0;
	public final static int TYPE_SYNC = 1;
	public final static int TYPE_CONTROL = 2;
	public final static int ACTION_DDEFAULTFILE = 1;
	public final static int UPGRADE_APP = 6001;
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	}
	
	
	/**
	 * Description  
	 * @see Application#onTerminate()
	 */ 
		
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		
	}
	
}
