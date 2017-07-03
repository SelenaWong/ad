package com.app.ad.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/***
 * 
 * @Description Activity 基类 
 * @author Selena Wong
 * @date 2016年6月20日 上午11:26:58 
 * @version V1.3.1
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setRequestedOrientation(ActivityInfo
			        .SCREEN_ORIENTATION_LANDSCAPE);// 横屏 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕长亮   
		initVariable();
		initView(savedInstanceState);
		initData();
	}

	protected abstract void  initVariable();
	protected abstract void  initView(Bundle savedInstanceState);
	protected abstract void  initData();
}
