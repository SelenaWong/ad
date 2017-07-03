/*
 * $Id$
 */

package com.app.ad.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class ConnectionLog
{
	private String mPath;
	private Writer mWriter;
	private long lastTime=0;

	private static final SimpleDateFormat TIMESTAMP_FMT = 
	  new SimpleDateFormat("[HH:mm:ss] ");

	public ConnectionLog()
	  throws IOException
	{
		lastTime= ( new Date()).getTime();
		File sdcard = Environment.getExternalStorageDirectory();
        File logDir = new File(sdcard, "tokudu/log/");
        if (!logDir.exists()) {
        	logDir.mkdirs();
        	// do not allow media scan
            new File(logDir, ".nomedia").createNewFile();
        }        
		open(logDir.getAbsolutePath());
	}

	public ConnectionLog(String basePath)
	  throws IOException
	{
		open(basePath);
	}

	protected void open(String basePath)
	  throws IOException
	{	
		File f = new File( basePath+"/push-"+getTodayString()+".log" );
		mPath = f.getAbsolutePath();
		mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
		println("Opened log.");
	}

	private static String getTodayString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public String getPath()
	{
		return mPath;
	}

	public void println(String message)
	  throws IOException
	{
		if( ( new Date()).getTime()-lastTime >= 24*3600*1000){//24*3600*1000
			this.close();
			lastTime = (new Date()).getTime();
			File sdcard = Environment.getExternalStorageDirectory();
	        File logDir = new File(sdcard, "tokudu/log/"); 
			open(logDir.getAbsolutePath());
		}
		mWriter.write(TIMESTAMP_FMT.format(new Date()));
		mWriter.write(message);
		mWriter.write('\n');
		mWriter.flush();
	}
	
	 public void delExpiredFile(  ){
		 File sdcard = Environment.getExternalStorageDirectory();
	     File logDir = new File(sdcard, "tokudu/log/");
	     if (!logDir.exists()) {
	          return;
	     }
	     File childFile[] = logDir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String filename) {

				if(filename.startsWith("push-")&&filename.endsWith(".log")){
					int lastIndex = filename.indexOf(".");
					String name= filename.substring(5,lastIndex);
					Date date = new Date(name);
					long currentTime = System.currentTimeMillis();
					long createTime = date.getTime();
					if( currentTime-createTime>=24*60*60*1000){
						return true;
					}
				}
				return false;
			}
	     });
	     
	     for(File file : childFile){
	    	 file.deleteOnExit();
	     }
	 }
	
	
	
	public void close()
	  throws IOException
	{
		mWriter.close();
		mWriter =null;
	}
}
