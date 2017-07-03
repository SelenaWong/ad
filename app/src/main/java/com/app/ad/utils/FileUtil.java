
/**   
 * @Title: FileUtil.java 
 * @Package: com.app.ad.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年7月28日 下午8:46:45 
 * @version 1.3.1 
 */


package com.app.ad.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年7月28日 下午8:46:45 
 * @version V1.3.1
 */

public class FileUtil {
	
	public static File GetSDcardPath() {
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 获取路径为：SDcard/data/data/包名/cache
			file = Environment.getExternalStorageDirectory();
		}
		return file;
	}

	public static void SearchFile(String type, File file, List<String> fileList) {

		File files[] = file.listFiles();
		if (files == null) {
			return;
		}
		int len = files.length;
		for (int i = 0; i < len; i++) {// 文件夹
			if (!files[i].isDirectory()) {
				if (files[i].canWrite()) {
					String fileName = files[i].getName();
					if (fileName.indexOf(".") > 1) {
						fileName = fileName
								.substring(fileName.lastIndexOf(".") + 1);
						if (type.equals(fileName)) {
							String name = files[i].getAbsolutePath();
							fileList.add(name);
							Log.i("TAG", "添加文件" + name);
						}
					}
				}
			} else {
				SearchFile(type, files[i], fileList);
			}

		}
	}
		 
	    public static List<String>  ListFiles(String dir) {
	        List<String> allVideoList = new ArrayList<String>();
	        File directory = new File(dir);
	        if (!directory.exists()) {
	            System.out.println(String.format("Directory %s does not exist", dir));
	            return allVideoList;
	        }	 
	        if (!directory.isDirectory()) {
	            System.out.println(String.format("Provided value %s is not a directory", dir));
	            return  allVideoList;
	        }	 
	        File[] files = directory.listFiles(filefilter);
	        for (File f : files) {
	            System.out.println(f.getName());
	            allVideoList.add(f.getAbsolutePath());
	        }
	        return allVideoList;
	    }
	 
	    //create a FileFilter and override its accept-method
	    private static FileFilter filefilter = new FileFilter() {
	 
	        public boolean accept(File file) {
	            //if the file extension is .txt return true, else false
	        	   String name = file.getName();  
	                int i = name.indexOf('.');  
	                if (i != -1) {  
	                    name = name.substring(i);  
	                    if (name.equalsIgnoreCase(".mp4")  
	                            || name.equalsIgnoreCase(".3gp")  
	                            || name.equalsIgnoreCase(".wmv")  
	                          //  || name.equalsIgnoreCase(".ts")  
	                          //  || name.equalsIgnoreCase(".rmvb")  
	                            || name.equalsIgnoreCase(".mov")  
	                            || name.equalsIgnoreCase(".m4v")  
	                            || name.equalsIgnoreCase(".avi")  
	                           // || name.equalsIgnoreCase(".m3u8")  
	                          //  || name.equalsIgnoreCase(".3gpp")  
	                          //  || name.equalsIgnoreCase(".3gpp2")  
	                            || name.equalsIgnoreCase(".mkv")  
	                           // || name.equalsIgnoreCase(".flv")  
	                            || name.equalsIgnoreCase(".divx")  
	                            || name.equalsIgnoreCase(".f4v")  
	                          //  || name.equalsIgnoreCase(".rm")  
	                           // || name.equalsIgnoreCase(".asf")  
	                           // || name.equalsIgnoreCase(".ram")  
	                            || name.equalsIgnoreCase(".mpg")  
	                           // || name.equalsIgnoreCase(".v8")  
	                          //  || name.equalsIgnoreCase(".swf")  
	                         //   || name.equalsIgnoreCase(".m2v")  
	                          //  || name.equalsIgnoreCase(".asx")  
	                          //  || name.equalsIgnoreCase(".ra")  
	                          //  || name.equalsIgnoreCase(".ndivx")  
	                            || name.equalsIgnoreCase(".xvid")) {  
	                       // String vi = new String();  
//	                        vi.setDisplayName(file.getName());  
//	                        vi.setPath(file.getAbsolutePath()); 
	                        return true;  
	                    }  
	                   
	                } 
	                return false; 
	        }
	    };
	 
	    public static void test( ) {
	        FileUtil fileutil = new FileUtil();
	        fileutil.ListFiles("C:\\temp");
	    }
	
}
