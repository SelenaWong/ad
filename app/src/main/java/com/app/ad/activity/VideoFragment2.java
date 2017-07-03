package com.app.ad.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.ad.R;
import com.app.ad.entity.Path;
import com.app.ad.utils.FileUtil;
import com.app.ad.utils.ToastUtil;
import com.app.ad.widget.MyPlayer;

public class VideoFragment2 extends Fragment  implements OnPreparedListener
{
	private final String TAG = "AdActivity";
	private SurfaceView sv;
	private MyPlayer myPlayer;
	RelativeLayout layout_video = null;
	int which_file=0;
	private String 	adPath = null; //Environment.getExternalStorageDirectory().getPath()+dictory+"海底左边投影.wmv";
	private String winPricePath = null;// Environment.getExternalStorageDirectory().getPath()+dictory+"0424探测海底石油海水.mp4";
	private boolean blChange = true;
	private List<Path> mPaths = new ArrayList<Path>();
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);  
	    sv=(SurfaceView)rootView.findViewById(R.id.sv);
        myPlayer = new MyPlayer(sv,mPaths.get(0).getFilePath());
        return rootView;
    }
    
    public List<Path> getPaths(){
    	return mPaths;
    }
    
    public void setPaths(List<Path> paths){
    	this.mPaths = paths;
    }
    
    public String getAdFile(){
    	return this.adPath;
    }
    public void setAdFile(String filePath){
    	this.adPath = filePath;
    }
    public void setPriceFile(String filePath){
    	this.winPricePath = filePath;
    }
    public String getPriceFile(){
    	return this.winPricePath;
    }
    

	public void log(String message){
		log(message,null);
	}
	
	public void log(String message,Throwable e){
		try{
			if(e!=null){
				Log.e(TAG, e.getMessage());
			}else{
				Log.i(TAG, message);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
    
	public void start(){
		if( myPlayer!=null  && myPlayer.IsPlay()){
			myPlayer.pause();
		}else if(myPlayer!=null && !myPlayer.IsPlay()){
			myPlayer.play();
			return;
		}else {
			play(which_file);
		}
	}

	/*
	 * 停止播放
	 */
	public void stop() {
		if (myPlayer != null && myPlayer.IsPlay()){
			myPlayer.stop();
			myPlayer.release();
		}
		myPlayer = null;
	}

	/**
	 * 开始播放
	 * 
	 * @param msec 播放初始位置    
	 */
	protected void play( int which) {
		
		try {			
			if( which<0||which>=mPaths.size()){
				return;
			}
			String path = mPaths.get(which).getFilePath();
			if(TextUtils.isEmpty(path)){
				return;
			}
			if(which!=which_file){
				blChange = true;
				which_file = which;
			}
			Log.i(TAG,"path= "+path);
			File file = new File(path);
			if (!file.exists()) {
				ToastUtil.ShowText(getActivity().getApplicationContext(), "视频文件路径错误");
				Log.i(TAG,"视频文件路径错误");
				return;
			}
			if(myPlayer==null){
				myPlayer = new MyPlayer(sv,mPaths.get(0).getFilePath());
			}
			myPlayer.playUrl(path);
			blChange =false;
		} catch (Exception e) {
			Log.e(TAG, "Exception:"+e.toString());
			Log.i(TAG,e.toString());
			e.printStackTrace();
		}
	}
	
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 5001:
				Bundle bd = msg.getData();
				int index = bd.getInt("whichfile");
				play(index);
				break;
			}	
		}
	};

	boolean isPlaying(){
		if(myPlayer==null){
			return false;
		}
		else if(myPlayer.IsPlay()){
			return true;
		}
		return false;
	}
	
    
	@Override
	public void onDestroy() {
		try{
			if(myPlayer!=null){
				myPlayer.stop();
				myPlayer.release();
				myPlayer=null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		super.onDestroy();
	}


	/**
	 * Description 
	 * @param mp 
	 * @see OnPreparedListener#onPrepared(MediaPlayer)
	 */ 
		
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.start();
	}

}
