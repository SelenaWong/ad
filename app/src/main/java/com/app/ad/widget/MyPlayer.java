package com.app.ad.widget;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.app.ad.entity.WinPriceCompletedEvent;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import de.greenrobot.event.EventBus;
import android.widget.SeekBar;

/**
 *  视频播放的控制工具类，提供播放，暂停等功能
 *  
 *  @author qinchuo 2016-2-16
 */
public class MyPlayer implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback,OnVideoSizeChangedListener
{
	private int videoWidth;
	private int videoHeight;
	public MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	public Timer mTimer;
	private SurfaceView surfaceView;
	private String defaultPath="";
	private String currentPath="";


	public MyPlayer(SurfaceView surfaceView,String defaultPath )
	{
		this.surfaceView = surfaceView;
		this.defaultPath = defaultPath;
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void initPlayer(){
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		try {
			if(!TextUtils.isEmpty(defaultPath)){
				mediaPlayer.setDataSource(defaultPath);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public boolean IsPlay(){
		if(mediaPlayer==null){
			return false;
		}
		else if(mediaPlayer.isPlaying()){
			return true;
		}
		return false;
	}
	
	
	public void play()
	{
		mediaPlayer.start();
	}

	public void playUrl(String videoUrl)
	{
		try
		{
			if(TextUtils.isEmpty(videoUrl)){
			   return;
			}
			currentPath = videoUrl;
			if(mediaPlayer==null){
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setLooping(false);
				mediaPlayer.setDisplay(surfaceView.getHolder());
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediaPlayer.setOnCompletionListener(this);
				mediaPlayer.setOnVideoSizeChangedListener(this);
				mediaPlayer.setOnErrorListener(new OnErrorListener()
				{
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra)
					{
						Log.d("mediaPlayer","what = "+what+",extra = "+extra);
						playUrl(defaultPath);
						return false;
					}
				});
			}
			mediaPlayer.reset();			
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepareAsync();
		}
		catch (IllegalArgumentException e)
		{
			Log.i("MyPlayer", "IllegalArgumentException:"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			Log.i("MyPlayer", "IllegalStateException:"+e.getMessage());
			e.printStackTrace();
			playUrl( videoUrl);
		}
		catch (IOException e)
		{
			Log.i("MyPlayer", "IOException:"+e.getMessage());
			e.printStackTrace();
		}
	}

	public void pause()
	{
		if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}

	public void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void release()
	{
		if(mediaPlayer!=null){
			mediaPlayer.release();
		}
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		Log.d("mediaPlayer", "surface changed");
	}

	
	@Override
	public void surfaceCreated(SurfaceHolder arg0)
	{
		try
		{
			Log.d("mediaPlayer", "surface created");
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setLooping(false);
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnErrorListener(new OnErrorListener()
			{
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra)
				{
					Log.d("mediaPlayer","what = "+what+",extra = "+extra);
					playUrl(defaultPath);
					return false;
				}
			});
			
			this.playUrl(defaultPath);
		}
		catch (Exception e)
		{
			Log.e("mediaPlayer", "error", e);
			Log.i("MyPlayer", "surfaceCreated Exception:"+e.getMessage());
			
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0)
	{
		Log.d("mediaPlayer", "surface destroyed");
	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0)
		{
			mp.start();
		}
		Log.d("mediaPlayer", "onPrepared : "+mp.getDuration());
	}

	@Override
	public void onCompletion(MediaPlayer player)
	{
		
		if(currentPath==defaultPath){
			Log.d("mediaPlayer", "onCompletion replay ");
			player.seekTo(0);
			player.start();
		}else{
			Log.d("mediaPlayer", "onCompletion replay default file");
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer=null;
			playUrl(defaultPath);
			EventBus.getDefault().post(new WinPriceCompletedEvent());
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress)
	{

		Log.e("TAG", bufferingProgress + "% buffer");
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		//Log.d("mediaPlayer", "mp.getDuration() = "+mp.getDuration()+",w = "+surfaceView.getWidth()+",width = "+width+",height = "+height);
		if (width == 0 || height == 0)
		{
			return;
		}
		int w = surfaceView.getWidth();
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, w * height / width);
		surfaceView.setLayoutParams(layoutParams);
	}
	

}