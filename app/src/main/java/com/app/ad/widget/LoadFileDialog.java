/**    
 * 文件名：LogisticPickupPopupWindow.java    
 *    
 * 版本信息：    
 * 日期：2015年8月13日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.app.ad.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.ad.R;
import com.app.ad.application.AdApplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;



/**
 * 
 * 类名称：LogisticPickupPopupWindow 类描述： 创建人：wesley 创建时间：2015年8月13日 下午1:50:56
 * 
 */
public class LoadFileDialog extends Dialog implements OnClickListener {

	private ListView mListView;
	private TextView mHintTv;
	private LinearLayout mHintLl;
	private List<String> mList = new ArrayList<String>();
	private FileAdapter mAdapter = null;
	public  Handler mHandler;
	private View contentView;
	private RadioButton mOkBtn;
	private RadioButton mCancelBtn;
	private Context mContext;
	private ChooseFileListener mListener;
	private int mPosition = -1;
	private String defaultFile;

	@SuppressWarnings("deprecation")
	public LoadFileDialog(Context context, int theme, List<String> list,String defaultFile,
			ChooseFileListener listener) {
		super(context, theme);
		mContext = context;
		contentView = LayoutInflater.from(context).inflate(
				R.layout.load_file_dialog, null);
		mListView = (ListView) contentView.findViewById(R.id.lv_loadfile);
		mOkBtn = (RadioButton) contentView
				.findViewById(R.id.rb_loadfile_popwin_ok);
		mCancelBtn = (RadioButton) contentView
				.findViewById(R.id.rb_loadfile_popwin_cancel);
		mListener = listener;
		mOkBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		mPosition = -1;
		this.defaultFile = defaultFile;
		SetView(list);
		this.setContentView(contentView);
		// this.setWidth(LayoutParams.WRAP_CONTENT);
		// this.setHeight(LayoutParams.WRAP_CONTENT);
		// this.setBackgroundDrawable(new BitmapDrawable());
		// this.setFocusable(true);
		// this.setAnimationStyle(R.anim.popwindow);
	}


	public void SetView(List<String> list) {
		try {
			mList = list;
			mAdapter = new FileAdapter(mContext, mList, defaultFile);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					mPosition = arg2;
					defaultFile = mList.get(arg2);
					mAdapter.setDefaultFile(defaultFile);
					mAdapter.notifyDataSetChanged();
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rb_loadfile_popwin_ok:
			if (mList != null && mPosition >= 0 && mPosition < mList.size()) {
				String fileName = mList.get(mPosition);
				this.mListener.choosefile(fileName);
			}
			this.dismiss();
			break;
		case R.id.rb_loadfile_popwin_cancel:
			this.dismiss();
			break;
		default:
			break;
		}
	}

	public interface ChooseFileListener {

		public void choosefile(String fileName);
	}

}
