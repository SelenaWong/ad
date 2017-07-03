
/**   
 * @Title: TvService.java 
 * @Package: com.app.ad.application 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年11月13日 下午5:29:57 
 * @version 1.3.1 
 */


package com.app.ad.application;

import java.io.IOException;
import java.util.List;

import com.app.ad.activity.SplashActivity2;
import com.app.ad.utils.ConnectionLog;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年11月13日 下午5:29:57 
 * @version V1.3.1
 */

public class TvStartReceiver extends BroadcastReceiver {  
    public TvStartReceiver() {  
    }  
  
    @Override  
    public void onReceive(Context context, Intent intent) {  
 
            Log.w("AdActivity", "TV 启动了啊");  
            Intent serviceIntentt = new Intent(context,SplashActivity2.class);
            serviceIntentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(serviceIntentt);
    }  
  

}  