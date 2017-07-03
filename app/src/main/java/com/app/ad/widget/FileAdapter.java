package com.app.ad.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ad.R;
import com.app.ad.application.AdApplication;


public class FileAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mList = new ArrayList<String>();
	private LayoutInflater mInflater;
	private String defaultFile;

	public FileAdapter(Context context, List<String> list, String defaultFile) {

		mContext = context;
		mList = list;
		this.defaultFile = defaultFile;
		mInflater = LayoutInflater.from(mContext);
	}
    
	public void setDefaultFile(String file){
		this.defaultFile = file;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != mList) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (null != mList && position >= 0 && position < mList.size()) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (null != mList && position < mList.size()) {
			return position;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold viewHold;
		if (convertView == null) {
			viewHold = new ViewHold();
			convertView = mInflater.inflate(R.layout.file_list_item, null,
					false);
			viewHold.fileNameTv = (TextView) convertView
					.findViewById(R.id.tv_filelist_name);
			viewHold.defaultImv = (ImageView) convertView
					.findViewById(R.id.iv_filelist_isdefault);
			convertView.setTag(viewHold);
		} else {

			viewHold = (ViewHold) convertView.getTag();
		}
		
		String fileName = mList.get(position);
		if(defaultFile.equals(fileName)){
			viewHold.defaultImv.setImageResource(R.drawable.file_isdefault);
			
		}else{
			viewHold.defaultImv.setImageResource(R.drawable.file_isnotdefault);
		}
		
		int index = fileName.lastIndexOf("/");
		if (fileName.length() > index + 1) {
			fileName = fileName.substring(index + 1);
			viewHold.fileNameTv.setText(fileName);
		}
		return convertView;
	}

	private class ViewHold {
		private TextView fileNameTv;
		private ImageView defaultImv;
	}

/*	private class OnItemChildClickListener implements View.OnClickListener {

		private int mActionType;
		private int mPosition;

		public OnItemChildClickListener(int atype, int position) {

			mActionType = atype;
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	}*/
}
