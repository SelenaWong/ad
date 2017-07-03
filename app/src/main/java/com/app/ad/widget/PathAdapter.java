
/**   
 * @Title: PathAdapter.java 
 * @Package: com.app.ad.widget 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年12月12日 下午4:02:01 
 * @version 1.3.1 
 */


package com.app.ad.widget;

import java.util.ArrayList;
import java.util.List;

import com.app.ad.R;
import com.app.ad.entity.Path;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年12月12日 下午4:02:01 
 * @version V1.3.1
 */

public class PathAdapter extends BaseAdapter {
	
	private List<Path> list = new ArrayList<Path>();
	private Context context;
	private OnItemEditListener listener;
	
	public PathAdapter(Context context,List<Path> paths,OnItemEditListener listener){
		this.context = context;
		this.list = paths;
		this.listener = listener;
	}
	
	/**
	 * Description 
	 * @return 
	 * @see android.widget.Adapter#getCount() 
	 */ 
		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list==null){
			return 0;
		}
		return list.size();
	}

	/**
	 * Description 
	 * @param position
	 * @return 
	 * @see android.widget.Adapter#getItem(int) 
	 */ 
		
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(list==null){
			return null;
		}
		return list.get(position);
	}

	/**
	 * Description 
	 * @param position
	 * @return 
	 * @see android.widget.Adapter#getItemId(int) 
	 */ 
		
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Description 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return 
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */ 
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold hold;
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_path,null);
			hold = new ViewHold();
			hold.mNameTv = (TextView)convertView.findViewById(R.id.item_path_name);
			hold.mPathTv = (TextView)convertView.findViewById(R.id.item_path_txt);
			hold.mEdtTv = (TextView)convertView.findViewById(R.id.item_path_edit);
			convertView.setTag(hold);
		}else{
			hold = (ViewHold) convertView.getTag();
		}		
		hold.mNameTv.setText(list.get(position).getName());
		hold.mPathTv.setText(list.get(position).getFilePath());
		final int pos = position;
		hold.mEdtTv.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(listener!=null){
					listener.onItemEditListener(pos);
				}
			}
		});
		return convertView;
	}
	
	
	public class ViewHold{
		private TextView mNameTv;
		private TextView mPathTv;
		private TextView mEdtTv;
	}
	
	public interface OnItemEditListener{
		public void onItemEditListener(int position);
	}

}
