
/**   
 * @Title: PathHelper.java 
 * @Package: com.app.ad.net 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年12月12日 下午4:42:24 
 * @version 1.3.1 
 */


package com.app.ad.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.ad.entity.Path;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年12月12日 下午4:42:24 
 * @version V1.3.1
 */

public class PathHelper {
	
	
	public static List<Path> initPath(List<Path> paths){
		int size = 11;
		if(paths==null){
			paths = new ArrayList<Path>();
		}else{
			paths.clear();
		}
		paths.add(new Path( "广告视频",""));
		for(int i=1;i<size;i++){
			paths.add(new Path("中奖视频"+Integer.toString(i),""));
		}
		return paths;
	}
	
	
	public static List<Path>  setPathData(List<Path> paths,Context context){
		
		if(paths==null){
			return null;
		}
		SharedPreferences sp = context.getSharedPreferences("video",Context.MODE_PRIVATE);
		int size = 11;
		for(int i=0;i<size;i++){
			String filePath = sp.getString(paths.get(i).getName(),"");
			paths.get(i).setFilePath(filePath);
		}
		return paths;
	}

   public static boolean  IsEmpthPath(List<Path> paths){
		
		if(paths==null){
			return true;
		}
		int size =paths.size();
		if(size==0){
			return true;
		}
		File file=null;
	/*	for(int i=0;i<size;i++){
			String filePath = paths.get(i).getFilePath();
			 file = new File(filePath);
			if(TextUtils.isEmpty(filePath)||!file.exists()){
				return true;
			}
		}*/
		String filePath = paths.get(0).getFilePath();
		 file = new File(filePath);
		if(TextUtils.isEmpty(filePath)||!file.exists()){
			return true;
		}
		return false;
	 }

	
	
}
