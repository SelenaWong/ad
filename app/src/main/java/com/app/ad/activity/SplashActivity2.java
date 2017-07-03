package com.app.ad.activity;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.ad.R;
import com.app.ad.application.AdApplication;
import com.app.ad.entity.Path;
import com.app.ad.net.PathHelper;
import com.app.ad.utils.FileUtil;
import com.app.ad.utils.NetUtil;
import com.app.ad.utils.ToastUtil;
import com.app.ad.widget.LoadFileDialog;
import com.app.ad.widget.LoadFileDialog.ChooseFileListener;
import com.app.ad.widget.PathAdapter.OnItemEditListener;
import com.app.ad.widget.PathAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.textservice.TextServicesManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SplashActivity2 extends BaseActivity implements OnClickListener{
	
	private TextView mIPTv,mPortTv;
	private Button mConfirmBtn;
	private LinearLayout mConfigurateLl;
	private SplashActivity2 me;
	private ListView mPathLv;
	private List<Path> mPaths = new ArrayList<Path>();
	private PathAdapter mAdapter;
	private String ipInfo;

    
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_splash2);
		me = SplashActivity2.this;
		mPaths = PathHelper.initPath(mPaths);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mIPTv = (TextView) findViewById(R.id.configurate_txt_ipAddress);
		mPortTv = (TextView) findViewById(R.id.configurate_txt_portAddress);
		mPathLv = (ListView) findViewById(R.id.lv_path);
		mConfirmBtn = (Button) findViewById(R.id.configurate_btn_confirm);
		mConfigurateLl = (LinearLayout) findViewById(R.id.configurate_ll);
		setParams();
	}
	
	public void setParams(){
		ipInfo = NetUtil.getIpAddress();
		if(TextUtils.isEmpty(ipInfo)){
			ToastUtil.ShowText(getApplicationContext(), "小助手提醒您：网络未连接噢~~");
		}else{
			mIPTv.setText(ipInfo);
		}
		mPortTv.setText("5001");
		mConfirmBtn.setEnabled(false);
		loadFile();
		mConfirmBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.configurate_btn_confirm:
			if(PathHelper.IsEmpthPath(mPaths) || TextUtils.isEmpty(ipInfo) ){
				return;
			}
			ToastUtil.ShowText(getApplicationContext(), "配置成功，现在进入石油开采界面~~");
			gotoAdActivity();
			break;
		default:
			break;
		}
	}

	public void savePath(){
		SharedPreferences sp = getSharedPreferences("video",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		int size = mPaths.size();
		for(int i=0;i<size;i++){
			editor.putString(mPaths.get(i).getName(), mPaths.get(i).getFilePath());
		}
		editor.commit();
		if(!PathHelper.IsEmpthPath(mPaths)){
			mConfirmBtn.setEnabled(true);
		}
	}

	public void saveLaunchCount( ){
		SharedPreferences sp = getSharedPreferences("launchCount",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("IsFirstLaunch", false);
		editor.commit();
	}
	
	public boolean isFirstLaunch( ){
		SharedPreferences sp = getSharedPreferences("launchCount",Context.MODE_PRIVATE);
		boolean isFirstLauncher = sp.getBoolean("IsFirstLaunch", true);
		return isFirstLauncher;
	}

	public void loadFile(){
		try{	
			mPaths = PathHelper.setPathData(mPaths, SplashActivity2.this);
			mAdapter = new PathAdapter(SplashActivity2.this,mPaths,new OnItemEditListener (){
				@Override
				public void onItemEditListener(int position) {
					// TODO Auto-generated method stub
					ShowLoadFileDialog(mIPTv, position, mPaths.get(position).getFilePath());
				}
			});
			mPathLv.setAdapter(mAdapter);
			if( !PathHelper.IsEmpthPath(mPaths) &&!TextUtils.isEmpty(ipInfo)){
				gotoAdActivity();
			}else{
				mConfigurateLl.setVisibility(View.VISIBLE);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void gotoAdActivity( ){
		if( PathHelper.IsEmpthPath(mPaths)||TextUtils.isEmpty(ipInfo) ){
			return;
		}
		//ToastUtil.ShowText(getApplicationContext(), "配置成功，现在进入石油开采界面~~");
		Intent it = new Intent(SplashActivity2.this,AdActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Paths",(Serializable)mPaths);
		it.putExtras(bundle);
		startActivity(it);
		finish();
	}
	

	
	public void ShowLoadFileDialog(View parent, final int position,final String defaultFile) {
		try {
			File rootfile = FileUtil.GetSDcardPath();
			if (rootfile == null) {
				return;
			}
			File file = new File(rootfile, "tokudu");
			if (!file.exists()) {
				file.mkdir();
			}
			List<String> files = new ArrayList<String>();
			files = FileUtil.ListFiles(file.getAbsolutePath());
			if (files.size() < 1) {
				ToastUtil.ShowText(getApplicationContext(), "文件没有查找到");
				return;
			}
			LoadFileDialog lfDialog = new LoadFileDialog(SplashActivity2.this, R.style.MyAlertDialogStyle, files,defaultFile,
					new ChooseFileListener() {
						@Override
						public void choosefile(String fileName) {
							// TODO Auto-generated method stub
							if(!TextUtils.isEmpty(fileName)&&!defaultFile.equals(fileName)){
								ToastUtil.ShowText(getApplicationContext(), "文件保存成功~~");
								mPaths.get(position).setFilePath(fileName);
								mAdapter.notifyDataSetChanged();
								savePath();
							}
						}
					});
			lfDialog.show();
			WindowManager.LayoutParams params = lfDialog.getWindow().getAttributes();
			WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();
			params.width = width / 2;
			lfDialog.getWindow().setAttributes(params);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		
		}
	}

	
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
	}

	private StringBuilder logBuilder = new StringBuilder();
	private SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

	
	@Override
	protected void onResume() {
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
		HideSystemUI();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		HideSystemUI();
	}

	
	public void HideSystemUI() {
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
	}

}
