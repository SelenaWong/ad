package com.app.ad.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.app.ad.R;
import com.app.ad.application.AdApplication;
import com.app.ad.entity.Path;
import com.app.ad.entity.TcpEvent;
import com.app.ad.entity.WinPriceCompletedEvent;

import com.app.ad.global.Constant;
import com.app.ad.utils.CommonUtil;
import com.app.ad.utils.FileUtil;
import com.app.ad.utils.NetUtil;
import com.app.ad.utils.ToastUtil;
import com.app.ad.waylau.netty.demo.simplechat.SimpleChatServerInitializer;
import com.app.ad.widget.LoadFileDialog;
import com.app.ad.widget.LoadFileDialog.ChooseFileListener;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class AdActivity extends BaseActivity {

	private static final int AUDIO_AD = 1003;
	private static final int AUDIO_WIN = 1004;
	private static final int AUDIO_LOCKSCREEN = 1005;

	private static String TAG = "AdActivity";
	private static Context context;
	private static int PORT = 5001;
	private ImageView mScreenImv;
	private FrameLayout mVideoFl;
	private VideoFragment2 vf = null;
	private AdActivity me;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private ChannelFuture f;
	private AdApplication app;
	private List<Path> mPaths = new ArrayList<Path>();

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		setContentView(R.layout.activity_ad);
		EventBus.getDefault().register(this);
		me = AdActivity.this;
		context = me;
		//serverThread = new ServerThread();
		//serverThread.start();
		mHandler.post(netRunnable);
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		try{
			mScreenImv = (ImageView) findViewById(R.id.imv_lockscreen);
			mVideoFl = (FrameLayout) findViewById(R.id.fl_video);
			Intent it = getIntent();
			Bundle bundle = it.getExtras();
			mPaths = (List<Path>) bundle.getSerializable("Paths");
			ShowVideoPlayer();	
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void ShowVideoPlayer() {
		vf = new VideoFragment2();
		vf.setPaths(mPaths);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_video, vf);
		ft.commitAllowingStateLoss();
	}

	
	
	
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
	}

	
	private boolean isNetEnable = false;
	private final static int NET_STATE_NORMAL = 201;
	private final static int NET_STATE_ERR = 202;

	// 定时检查网络情况
	private Runnable netRunnable = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
			if(mHandler!=null){
				mHandler.postDelayed(this, 10000);
			}
			boolean netState = CommonUtil.isNetworkAvailable(getApplicationContext());
			if(!netState){
			    Message msg = new Message();
			    msg.what = NET_STATE_ERR;
			    mHandler.sendMessage(msg);
				isNetEnable = false;
			    
			}else{
				 Message msg = new Message();
				 msg.what = NET_STATE_NORMAL;
				 mHandler.sendMessage(msg);
				 isNetEnable = true;
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	};
	
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			try{
			switch (msg.what) {
			case Constant.ACTION_READ:
				String recvData = msg.getData().getString("data").trim().replace("\r\n", "");
				Log.i(TAG," recvData= " + recvData);
				if (recvData.contains("STX@nzj")&&recvData.length()>=8) {
					String num = recvData.substring(7, 8);
					int index = Integer.parseInt(num);
					excuteWinPrice(index);
				}
				break;
			case AUDIO_LOCKSCREEN:
				Log.i(TAG, "lockscreen 2s");
				mScreenImv.setVisibility(View.VISIBLE);
				break;
			case AUDIO_AD:
				Log.i(TAG, "restart");
				if (blStartTimer) {
					stopLockScreenTimer();
				}
				mScreenImv.setVisibility(View.GONE);
				vf.play(0);
				break;
			case NET_STATE_ERR:
				try{
					Log.i(TAG, "net state err");
					if(serverStart){
						Log.i(TAG, "serverThread interrupted");
						serverThread.interrupted();
						serverThread = null;
					}
				}catch(Exception ex){
					ex.printStackTrace();
					
				}
				break;
			case  NET_STATE_NORMAL:
				Log.i(TAG, "net state normal");
				if(!serverStart){
					Log.i(TAG, "serverThread start");
					serverThread=new ServerThread();
					serverThread.start();
					serverStart = true;	
				}
				break;
			default:
				break;
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	};

	public void excuteWinPrice(int index) {
		try{
			vf.play(index);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void onEvent(WinPriceCompletedEvent event ){
		mHandler.post(lockScreenRunn);
	}

	
	private Runnable lockScreenRunn = new Runnable() {
		@Override
		public void run() {
			try{
			// TODO Auto-generated method stub
				if (!blStartTimer) {
					startLockScreenTimer();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	};


	public void onEvent(TcpEvent event) {
		try{
		switch (event.getActionType()) {
		case Constant.ACTION_ADD:
			Log.i(TAG, event.getIp() + "--added");
			break;
		case Constant.ACTION_REMOVE:
			Log.i(TAG, event.getIp() + "--remove");
			break;
		case Constant.ACTION_ACTIVE:
			Log.i(TAG, event.getIp() + "--active");
			break;
		case Constant.ACTION_INACTIVE:
			Log.i(TAG, event.getIp() + "--inactive");
			break;
		case Constant.ACTION_READ:
			Log.i(TAG, event.getIp() + "--read");
			String recvData = event.getMsg();
			if(TextUtils.isEmpty(recvData)){
				break;
			}
			Message msg = new Message();
			msg.what = Constant.ACTION_READ;
			Bundle rb = new Bundle();
			rb.putString("data", recvData);
			if (event.getChannel() != null) {
				msg.setData(rb);
				mHandler.sendMessage(msg);
			}
			break;
		case Constant.ACTION_EXCEPTION:
			Log.i(TAG, "action_exception");
			break;
		default:
			break;
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	   super.onResume();
	   HideSystemUI();
	};

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
			if (blStartTimer) {
				stopLockScreenTimer();
			}
			EventBus.getDefault().unregister(this);
			if(mHandler!=null){
				mHandler.removeCallbacks(lockScreenRunn);
				mHandler.removeCallbacks(netRunnable);
				mHandler.removeCallbacks(null);
				mHandler=null;
			}	
			if(serverStart&&serverThread!=null){
				serverThread.interrupt();
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	private boolean serverStart =false;
	private Thread serverThread;
	private class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				ServerBootstrap b = new ServerBootstrap();
				b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
						.childHandler(new SimpleChatServerInitializer()) // (4)
						.option(ChannelOption.SO_BACKLOG, 128) // (5)
						.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
				Log.i(TAG, "SimpleChatServer 启动了");
				// 绑定端口，开始接收进来的连接
				f = b.bind(PORT).sync(); // (7)
				// 等待服务器 socket 关闭 。
				// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
				f.channel().closeFuture().sync();
			}catch(InterruptedException ex){
				serverStart=false;
				serverThread=null;
				ex.printStackTrace();
			} catch (Exception ex) {
				serverStart=false;
				serverThread=null;
				ex.printStackTrace();
			} finally {
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		};
	};

	private Timer mLockScreenTimer;
	private TimerTask mTimerTask;
	private boolean blStartTimer = false;
	public static int LOCKSCREEN_TIME_COUNT = 0;
	public static int LOCKSCREEN_TIME_COME = 2;

	private void startLockScreenTimer() {
		try{
		if (mLockScreenTimer == null) {
			mLockScreenTimer = new Timer();
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LOCKSCREEN_TIME_COUNT++;
					if (LOCKSCREEN_TIME_COUNT >= LOCKSCREEN_TIME_COME) {
						Message msg = new Message();
						msg.what = AUDIO_AD;
						mHandler.sendMessage(msg);
					}
				}
			};
		}
		if (mLockScreenTimer != null && mTimerTask != null) {
			Message msg = new Message();
			msg.what = AUDIO_LOCKSCREEN;
			mHandler.sendMessage(msg);
			blStartTimer = true;
			LOCKSCREEN_TIME_COUNT = 0;
			mLockScreenTimer.schedule(mTimerTask, 0, 1000);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void stopLockScreenTimer() {
		try{
		if (mLockScreenTimer != null) {
			mLockScreenTimer.cancel();
			mLockScreenTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
		blStartTimer = false;
		LOCKSCREEN_TIME_COUNT = 0;
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
