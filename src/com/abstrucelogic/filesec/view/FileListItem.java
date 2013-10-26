package com.abstrucelogic.filesec.view;

import com.abstrucelogic.filesec.R;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileListItem extends RelativeLayout {
	
	//private Context mCurContext;
	
	private TextView mFileName;
	
	public FileListItem(Context context) {
		super(context);
		this.initView(context);
	}
	
	public void setName(String fName) {
		this.mFileName.setText(fName);
	}
	
	private void initView(Context context) {
		View infView = View.inflate(context, R.layout.file_list_item, null);
		this.addView(infView);
	}
}
